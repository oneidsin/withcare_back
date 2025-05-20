package com.withcare.admin.controller;

import com.withcare.admin.service.CrawlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class CrawlController {
    Logger logger = LoggerFactory.getLogger(getClass());

    Map<String, Object> result = null;

    @Autowired
    CrawlService svc;

    // 청년일보 크롤링
    @GetMapping("/{id}/saveCrawlPostYouth")
    public Map<String, Object> saveCrawlPostYouth(@PathVariable String id) {
        result = new HashMap<>();
        result.put("result", svc.saveCrawlPostYouth(id));
        return result;
    }
}
