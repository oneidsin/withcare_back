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
    CrawlManagementDAO dao;

    @Autowired
    CrawlService svc;

    // 3분마다 실행
    @Scheduled(cron = "0 */3 * * * *")
    public void checkCrawlSchedule() {
        List<CrawlManagementDTO> source_list = dao.getActiveCrawlSources();

        for (CrawlManagementDTO source : source_list) {
            if (!source.isCrawl_yn()) continue; // crawl_yn 이 false 면 스킵

            LocalDateTime last_crawl = source.getLast_crawl_at().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime next_crawl_time = last_crawl.plusMinutes(source.getCrawl_cycle());

            if (now.isAfter(next_crawl_time)) {
                log.info("크롤링 시작 : {}", source.getSource_name());

                // 카테고리로 분기 (예: 청년일보만)
                if ("youth".equals(source.getCrawl_cate())) {
                    svc.saveCrawlPostYouth(source.getCreate_admin_id());
                }

                // 크롤링 후 last_crawl_at 업데이트
                dao.updateLastCrawlAt(source.getSource_idx());

                log.info("크롤링 완료 및 마지막 크롤링 일시 업데이트 : {}", source.getSource_name());
            }
        }
    }
}
