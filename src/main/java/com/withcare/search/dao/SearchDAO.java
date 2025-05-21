
package com.withcare.search.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.search.dto.SearchDTO;
import com.withcare.search.dto.SearchResultDTO;

@Mapper
public interface SearchDAO {

		// 검색어 저장 목록
	 int insertSearch(SearchDTO dto);
	    List<SearchResultDTO> getSearchResult(SearchDTO dto);
	    
	    // 최근 검색어 목록 (선택사항)
	    List<SearchDTO> recentSearches(String user_id);
		Integer findBoardIdxForSearch(SearchDTO dto);
}
