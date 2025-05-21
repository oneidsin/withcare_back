package com.withcare.admin.scheduler;

import com.withcare.admin.dao.CrawlManagementDAO;
import com.withcare.admin.dto.CrawlManagementDTO;
import com.withcare.admin.service.CrawlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class CrawlCycleScheduler {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    CrawlManagementDAO crawlManagementDAO;

    @Autowired
    CrawlService svc;

    // CrawlCycleScheduler 클래스에 추가
    public void testScheduler() {
        // 현재 시간을 로깅
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        log.info("테스트 시작 시간: {}", now);

        // 스케줄러 로직 직접 호출
        checkCrawlSchedule();

        log.info("테스트 완료 시간: {}", now);
    }

    // 3분마다 실행
    @Scheduled(cron = "0 */3 * * * *")
    public void checkCrawlSchedule() {
        try {
            List<CrawlManagementDTO> source_list = crawlManagementDAO.getActiveCrawlSources();
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul")); // 현재 시간 설정

            for (CrawlManagementDTO source : source_list) {
                try {
                    boolean should_crawl = false;
                    int cycle_minutes = source.getCrawl_cycle(); // 분 단위

                    // 주기가 유효한지 확인
                    if (cycle_minutes <= 0) {
                        log.warn("유효하지 않은 크롤링 주기: {} 분, 출처: {}", cycle_minutes, source.getSource_name());
                        continue;
                    }

                    // 1. 주기 변경 시각 기준으로 오전 10시 설정
                    LocalDateTime updated_at = source.getCrawl_cycle_updated_at()
                            .toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

                    LocalDateTime base_time = updated_at.withHour(10).withMinute(0).withSecond(0);

                    // 2. 만약 updatedAt이 오전 10시 이후라면, 다음날 오전 10시로 기준 이동
                    if (updated_at.isAfter(base_time)) {
                        base_time = base_time.plusDays(1);
                    }

                    // 3. base_time 이후로 주기에 맞춰 반복 실행
                    LocalDateTime next_crawl_time = base_time;

                    while (next_crawl_time.plusMinutes(cycle_minutes).isBefore(now) ||
                            next_crawl_time.plusMinutes(cycle_minutes).isEqual(now)) {
                        next_crawl_time = next_crawl_time.plusMinutes(cycle_minutes);
                        should_crawl = true;
                    }

                    if (should_crawl) {
                        processCrawling(source);
                    }
                } catch (Exception e) {
                    log.error("출처 {} 처리 중 오류 발생: {}", source.getSource_name(), e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            log.error("크롤링 스케줄러 실행 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    // 크롤링 처리를 위한 별도 메서드
    private void processCrawling(CrawlManagementDTO source) {
        log.info("크롤링 시작 예정 : 출처 {}", source.getSource_name());

        String source_name = source.getSource_name();
        String admin_id = source.getCreate_admin_id();
        boolean crawled = false;

        try {
            switch (source_name) {
                case "청년일보":
                    svc.saveCrawlPostYouth(admin_id);
                    crawled = true;
                    break;
                case "하이닥":
                    svc.saveCrawlPostHidoc(admin_id);
                    crawled = true;
                    break;
                case "국민건강보험공단":
                    svc.saveCrawlPostInsurance(admin_id);
                    crawled = true;
                    break;
                default:
                    log.warn("알 수 없는 출처 이름입니다 : {} 소스 idx : {}", source_name, source.getSource_idx());
            }

            if (crawled) {
                crawlManagementDAO.updateLastCrawlAt(source.getSource_idx());
                log.info("크롤링 완료 및 마지막 크롤링 일시 업데이트 : {}", source_name);
            }
        } catch (Exception e) {
            log.error("출처 {} 크롤링 중 오류 발생: {}", source_name, e.getMessage(), e);
        }
    }
}
