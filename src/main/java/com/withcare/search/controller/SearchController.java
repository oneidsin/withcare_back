package com.withcare.search.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.profile.dto.ProfileDTO;
import com.withcare.search.dto.SearchDTO;
import com.withcare.search.dto.SearchResultDTO;
import com.withcare.search.service.SearchService;
import com.withcare.util.JwtToken.JwtUtils;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController {

    Logger log = LoggerFactory.getLogger(getClass());

    Map<String, Object> result;

    @Autowired
    private SearchService svc;
    
    @PostMapping
    public Map<String, Object> search(
            @RequestBody SearchDTO dto,
            @RequestHeader Map<String, String>header) {
        result = new HashMap<>();
        
        try {
            // 권한 체크
            String token = header.get("authorization");
            if (token == null || token.isEmpty()) {
                result.put("success", false);
                result.put("message", "로그인이 필요합니다.");
                result.put("redirect", "/login");  // 프론트엔드에서 리다이렉트할 URL
                return result;
            }

            String loginId = (String) JwtUtils.readToken(token).get("id");
            dto.setSch_id(loginId);
            
            // 1. 검색 결과 조회
            List<SearchResultDTO> searchResults = svc.getSearchResult(dto);
            int totalCount = svc.getSearchResultCount(dto);  // 전체 검색 결과 수
            int totalPages = (int) Math.ceil((double) totalCount / dto.getPageSize());  // 전체 페이지 수

            result.put("success", true);
            result.put("data", searchResults);
            result.put("totalPages", totalPages);
            result.put("currentPage", dto.getPage());
            result.put("totalCount", totalCount);
            
            // 2. 검색어 저장
            try {
                // 검색어가 비어있거나 null인 경우 저장하지 않음
                if (dto.getSch_keyword() != null && !dto.getSch_keyword().trim().isEmpty()) {
                    svc.insertSearch(dto);
                } else {
                    log.warn("빈 검색어 감지: 검색어 저장 건너뜀");
                    result.put("searchSaved", false);
                }
            } catch (Exception e) {
                log.error("검색어 저장 중 오류 발생", e);
                result.put("searchSaved", false);
            }
            
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
    		@PathVariable String sch_id,
    		@RequestHeader Map<String, String>header){
    	result = new HashMap<>();

	    
    	try {
    	    String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
    	    
        	List<SearchDTO> recentList = svc.searchRecent(loginId);
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
    		@PathVariable String sch_id,
    		@RequestHeader Map<String, String>header){
    	
    	result = new HashMap<>();
    	
    	try {
    	    String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
    	    
    		// 최근 검색어 10개 조회
        	List<SearchResultDTO>recommendPost = svc.recommendPost(loginId);
        	
        	result.put("success", true);
        	result.put("data", recommendPost);
		} catch (Exception e) {
			result.put("success", false);
		}
    	return result;
    }
    
    // 로그인 X or 검색 기록 X
    @GetMapping("/recommend/default")
    public Map<String, Object> recommendDefault(
    		@RequestHeader Map<String, String>header){
    	
    	result = new HashMap<>();
    	
    	try {
            String loginId = null;
            if (header != null && header.containsKey("authorization")) {
                loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
            }

            List<SearchResultDTO> recommendPost;
            if (loginId == null) {
                // 로그인 안 된 상태 → 기본 인기글 추천
                recommendPost = svc.recommendDefault();
            } else {
                // 로그인 O → 최근 검색어 기준 추천글 조회
                boolean hasSearchHistory = svc.searchHistory(loginId);
                if (hasSearchHistory) {
                    recommendPost = svc.recommendPost(loginId);
                } else {
                    recommendPost = svc.recommendDefault();
                }
            }
			result.put("success", true);
			result.put("data", recommendPost);
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
    @PostMapping("/cancer")
    public Map<String, Object> searchCancer(
    		@RequestHeader Map<String, String>header){
    	
        result = new HashMap<>();
		
        try {
            String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
            // 로그인 사용자 프로필 정보 가져오기
            ProfileDTO profileDTO = svc.profileCancer(loginId);
            profileDTO.setId(loginId);
            
            List<SearchResultDTO> results = svc.searchCancer(profileDTO);
            
            result.put("success", true);
            result.put("data", results);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "로그인 정보가 유효하지 않습니다.");
        }

        return result;
    }
    
}