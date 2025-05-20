package com.withcare.search.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.withcare.search.dto.SearchResultDTO;
import com.withcare.search.dto.SearchResponseDTO;
import com.withcare.search.service.SearchService;
import com.withcare.util.JwtToken;

@RestController
@CrossOrigin
public class SearchController {

	@Autowired SearchService svc;
    private Logger log = LoggerFactory.getLogger(getClass());


    @PostMapping("/search")
    public Map<String, Object> searchPosts(@RequestBody SearchResultDTO dto, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                result.put("status", "fail");
                result.put("message", "인증 토큰이 없습니다.");
                return result;
            }

            String token = authHeader.substring(7);
            Map<String, Object> claims = JwtToken.JwtUtils.readToken(token);
            String userId = (String) claims.get("id");

            if (userId == null || userId.isEmpty()) {
                result.put("status", "fail");
                result.put("message", "유효하지 않은 토큰입니다.");
                return result;
            }

            List<SearchResponseDTO> posts = svc.searchPosts(dto, userId);
            result.put("status", "success");
            result.put("data", posts);

        } catch (Exception e) {
            log.error("검색 중 오류 발생", e);
            result.put("status", "error");
            result.put("message", "서버 오류: " + e.getMessage());
        }

        return result;
    }
}