package com.withcare.admin.controller;

import com.withcare.admin.service.CrawlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class CrawlController {
    Logger log = LoggerFactory.getLogger(getClass());

    Map<String, Object> result = null;

    @Autowired
    CrawlService svc;

    // 청년일보 크롤링
    @GetMapping("/{id}/crawl/saveCrawlPostYouth")
    public Map<String, Object> saveCrawlPostYouth(@PathVariable String id) {
        result = new HashMap<>();
        result.put("result", svc.saveCrawlPostYouth(id));
        return result;
    }

    // 하이닥 크롤링
    @GetMapping("/{id}/crawl/saveCrawlPostHidoc")
    public Map<String, Object> saveCrawlPostHidoc(@PathVariable String id) {
        result = new HashMap<>();
        result.put("result", svc.saveCrawlPostHidoc(id));
        return result;
    }

    // 국민건강보험공단 크롤링
    @GetMapping("/{id}/crawl/saveCrawlPostInsurance")
    public Map<String, Object> saveCrawlPostInsurance(@PathVariable String id) {
        result = new HashMap<>();
        result.put("result", svc.saveCrawlPostInsurance(id));
        return result;
    }

    // 관리자가 크롤링 활성화 여부를 수정
    @PutMapping("/{id}/crawl/updateCrawlYn/{sourceIdx}")
    public Map<String, Object> updateCrawlYn(@PathVariable String id, @PathVariable int sourceIdx) {
        result = new HashMap<>();
        result.put("result", svc.updateCrawlYn(id, sourceIdx));
        return result;
    }
}
