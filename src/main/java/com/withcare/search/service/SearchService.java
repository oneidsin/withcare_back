package com.withcare.search.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.search.dao.SearchDAO;
import com.withcare.search.dto.SearchResultDTO;
import com.withcare.search.dto.SearchDTO;

@Service
public class SearchService {

    @Autowired SearchDAO dao;
    Logger log = LoggerFactory.getLogger(getClass());
    
    // 검색어 저장
    public int insertSearch(SearchDTO dto) {
    	 // 1. 게시글 하나 조회해서 board_idx 가져오기
        Integer boardIdx = dao.findBoardIdxForSearch(dto);
        if (boardIdx != null) {
            dto.setBoard_idx(boardIdx);
        } else {
            dto.setBoard_idx(0); // 또는 예외 처리
        }

        // 2. search 테이블에 저장
        return dao.insertSearch(dto);
    }

    // 검색 결과 조회
    public List<SearchResultDTO> getSearchResult(SearchDTO dto) {
        return dao.getSearchResult(dto);
    }

    
    
    // 향후 확장용: 최근 검색어 목록 조회 등 나중에 필요시 만들 것!
    public List<SearchDTO> recentSearches(String user_id) {
        return dao.recentSearches(user_id);
    }    
   
}
