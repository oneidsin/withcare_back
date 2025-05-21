package com.withcare.search.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.withcare.search.dto.SearchDTO;
import com.withcare.search.dto.SearchResultDTO;
import com.withcare.search.service.SearchService;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController {

    Logger log = LoggerFactory.getLogger(getClass());

    Map<String, Object> result;

    @Autowired
    private SearchService svc;
    
    @PostMapping
    public Map<String, Object> search(@RequestBody SearchDTO dto) {
        result = new HashMap<>();

        try {
            // 1. 검색어 저장 (search 테이블)
            svc.insertSearch(dto);

            // 2. 검색 결과 반환
            List<SearchResultDTO> searchResults = svc.getSearchResult(dto);
            result.put("success", true);
            result.put("data", searchResults);
        } catch (Exception e) {
            log.error("검색 처리 중 오류 발생", e);
            result.put("success", false);
            result.put("message", "검색 처리 중 오류가 발생했습니다.");
        }

        return result;
    }
}