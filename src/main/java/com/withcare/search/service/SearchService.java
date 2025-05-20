package com.withcare.search.service;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.search.dao.SearchDAO;
import com.withcare.search.dto.SearchResultDTO;
import com.withcare.search.dto.SearchResponseDTO;

@Service
public class SearchService {

    @Autowired SearchDAO dao;
    Logger log = LoggerFactory.getLogger(getClass());

    public List<SearchResponseDTO> searchPosts(SearchResultDTO dto, String userId) {
        List<SearchResponseDTO> results = dao.searchPosts(dto);

        // 키워드 포함 횟수 계산
        for (SearchResponseDTO post : results) {
            int count = countOccurrences(post.getPost_title(), dto.getKeyword());
            if ("title_content".equals(dto.getSearch_type())) {
                count += countOccurrences(post.getPost_content(), dto.getKeyword());
            }
            post.setKeyword_count(count);
        }

        // 관련도 기준 정렬
        Collections.sort(results, (a, b) -> b.getKeyword_count() - a.getKeyword_count());

        // 검색 기록 저장
        if (dto.getKeyword() != null && !dto.getKeyword().isBlank()) {
            dao.insertSearchHistory(userId, dto);
        }

        return results;
    }

    private int countOccurrences(String text, String keyword) {
        if (text == null || keyword == null) return 0;
        return (int) Pattern.compile(Pattern.quote(keyword)).matcher(text).results().count();
    }
}
