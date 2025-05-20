package com.withcare.admin.service;

import com.withcare.admin.dao.CrawlDAO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrawlService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    CrawlDAO dao;

    // 청년일보 크롤링
    public List<Map<String, Object>> saveCrawlPostYouth(String id) {
        // 크롬 드라이버 경로 설정
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // 옵션 객체 생성
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--start-maximized"); // 크롬 시작시 전체화면(현재는 X)
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);

        List<Map<String, Object>> results = new ArrayList<>();

        int page_num = 1; // 크롤링을 시작할 페이지
        final int MAX_PAGES = 1; // 크롤링이 끝나는 페이지

        // 크롤링을 시작할 URL
        String base_url = "https://www.youthdaily.co.kr/news/section.html?sec_no=51&page=";

        try {
            while (page_num <= MAX_PAGES) {
                String current_page = base_url + page_num;
                System.out.println("\n > 크롤링하는 페이지 : " + page_num);
                driver.get(current_page);
                Thread.sleep(2000);

                // 1. 링크를 미리 String으로 저장
                List<WebElement> article_elements = driver.findElements(
                        By.cssSelector("div.ara_001 ul.art_list_all li a")
                );
                List<String> article_urls = new ArrayList<>();
                for (WebElement element : article_elements) {
                    String href = element.getAttribute("href");
                    article_urls.add(href);
                }

                System.out.println("수집한 기사 링크 수: " + article_urls.size());

                // 2. 각 링크를 순회하며 기사 정보 가져오기
                for (int i = 0; i < article_urls.size(); i++) {
                    try {
                        String original_url = article_urls.get(i);

                        driver.get(original_url);
                        Thread.sleep(2000);

                        // 2-1. 제목 가져오기
                        String post_title = driver.findElement(By.cssSelector("div.art_top > h2")).getText();

                        // 제목 기준 중복 체크(DB 조회)
                        if (dao.duplicateUrl(post_title)) {
                            log.info("이미 수집된 기사 제목 : {}", post_title);
                            continue;
                        }

                        // 2-2. 본문 가져오기
                        String post_content = driver.findElement(By.cssSelector("div.smartOutput")).getText();
                        // 2-3. 본문에 기사 url 추가
                        post_content += "\n\n[출처] " + original_url;

                        // 2-3. 이미지
                        String img_url = "";
                        try {
                            // 본문 전체에서 가장 첫 번째 img 태그를 가져옴
                            WebElement img = driver.findElement(By.cssSelector("div.smartOutput img"));
                            img_url = img.getAttribute("src");
                            if (!img_url.startsWith("http") && !img_url.startsWith("https")) {
                                img_url = "https://www.youthdaily.co.kr" + img_url;
                            }
                        } catch (Exception e) {
                            System.out.println("이미지 없음");
                        }


                        // 크롤링한 데이터를 result 에 저장
                        Map<String, Object> result = new HashMap<>();
                        result.put("id", id); // 작성자 id
                        result.put("board_idx", 2);
                        result.put("post_title", post_title);
                        result.put("post_content", post_content);

                        // 크롤링한 데이터를 DB 에 insert
                        dao.insertCrawlPost(result);
                        log.info("크롤링 결과 : {}", result.get("post_idx"));
                        // 크롤링한 데이터 중 이미지가 있으면 file 테이블에 insert
                        if (!img_url.isEmpty()) {
                            result.put("file_url", img_url);
                            dao.insertCrawlFile(result);
                        }

                        // 크롤링한 결과 전체를 리스트로 묶어 리턴
                        results.add(result);

                        System.out.println("\n[기사 " + (i + 1) + "]");
                        System.out.println("제목: " + post_title);
                        System.out.println("본문: " + post_content);
                        System.out.println("이미지: " + img_url);

                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("기사 " + (i + 1) + " 처리 실패: " + e.getMessage());
                    }
                }

                page_num++;
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
            System.out.println("\n크롤링 완료!");
        }

        return results;
    }
}
