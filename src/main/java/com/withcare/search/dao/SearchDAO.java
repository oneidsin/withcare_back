
package com.withcare.search.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.search.dto.SearchDTO;
import com.withcare.search.dto.SearchResultDTO;

@Mapper
public interface SearchDAO {

		// 검색어 저장 목록
	 	int insertSearch(SearchDTO dto);
	 
	    List<SearchResultDTO> getSearchResult(SearchDTO dto);
	    
		Integer findBoardIdxForSearch(SearchDTO dto);
		
		boolean recentKeyword(String sch_id, String sch_keyword);

		List<SearchDTO> searchRecent(String sch_id);

		List<SearchResultDTO> recommedPost(String latestKeyword);

		List<SearchResultDTO> recommendDefault();

		List<Map<String, Object>> searchPopular();
}
