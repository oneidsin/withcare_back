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
    
    // 최근 검색어 조회
    @GetMapping("/recent/{sch_id}")
    public Map<String, Object>searchRecent(
    		@PathVariable String sch_id){
    	result = new HashMap<>();
    	try {
        	List<SearchDTO> recentList = svc.searchRecent(sch_id);
        	result.put("success", true);
        	result.put("data", recentList);
		} catch (Exception e) {
	    	result.put("success", false);
		}

    	return result;
    }
    
    // 추천 게시글 (가장 최근 검색어 1개 기반의 게시글 가져오기)
    @GetMapping("/recommend/{sch_id}")
    public Map<String, Object>recommendPost(
    		@PathVariable String sch_id){
    	
    	result = new HashMap<>();
    	try {
    		// 최근 검색어 10개 조회
        	List<SearchResultDTO>recommedPost = svc.recommedPost(sch_id);
        	result.put("success", true);
        	result.put("data", recommedPost);
		} catch (Exception e) {
			result.put("success", false);
		}
    	return result;
    }
    
    // 로그인 X or 검색 기록 X
    @GetMapping("/recommend/default")
    public Map<String, Object> recommendDefault(){
    	result = new HashMap<>();
    	try {
			List<SearchResultDTO> popularPost = svc.recommendDefault();
			result.put("success", true);
			result.put("data", popularPost);
		} catch (Exception e) {
			result.put("success", false);
		}
    	return result;
    }
    
    // 전체 인기 검색어
    @GetMapping("/popular")
    public Map<String, Object>searchPopular(){
    	result = new HashMap<>();
    	try {
			List<Map<String, Object>>keywordList = svc.searchPopular();
			result.put("success", true);
			result.put("data", keywordList);
		} catch (Exception e) {
			result.put("success", false);
		}
    	return result;
    }
    
    // 암 종류 및 병기를 제목 + 내용에 포함하는 게시글 조회
    // 둘 다 포함 > 암 종류만 포함 > 병기만 포함
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}