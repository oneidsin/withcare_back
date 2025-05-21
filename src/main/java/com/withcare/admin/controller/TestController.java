package com.withcare.admin.controller;

import com.withcare.admin.scheduler.CrawlCycleScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private CrawlCycleScheduler crawlCycleScheduler;

    @GetMapping("/crawl")
    public String testCrawlScheduler() {
        crawlCycleScheduler.testScheduler();
        return "크롤링 스케줄러 테스트가 시작되었습니다. 로그를 확인하세요.";
    }
}