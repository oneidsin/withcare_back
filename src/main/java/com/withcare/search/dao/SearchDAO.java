
package com.withcare.search.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.profile.dto.ProfileDTO;
import com.withcare.search.dto.SearchDTO;
import com.withcare.search.dto.SearchResultDTO;

@Mapper
public interface SearchDAO {

		// 검색어 저장 목록
	 	int insertSearch(SearchDTO dto);
	 
	    List<SearchResultDTO> getSearchResult(SearchDTO dto);
	    
		Integer findBoardIdxForSearch(SearchDTO dto);
		
		int recentKeyword(String sch_id, String sch_keyword);

		List<SearchDTO> searchRecent(String sch_id);

		List<SearchResultDTO> recommendPost(String latestKeyword);

		List<SearchResultDTO> recommendDefault();

		List<Map<String, Object>> searchPopular();

		List<SearchResultDTO> searchCancer(String cancerKeyword, String stageKeyword);

		Integer searchHistory(String loginId);

		ProfileDTO profileCancer(String loginId);
}
