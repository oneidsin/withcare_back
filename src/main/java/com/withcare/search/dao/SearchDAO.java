
package com.withcare.search.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.withcare.search.dto.SearchResponseDTO;
import com.withcare.search.dto.SearchResultDTO;

@Mapper
public interface SearchDAO {

	List<SearchResponseDTO> searchPosts(SearchResultDTO dto);

	int insertSearchHistory(@Param("userId") String userId, @Param("dto") SearchResultDTO dto);
	
}
