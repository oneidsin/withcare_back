package com.withcare.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.profile.dto.ProfileDTO;
import com.withcare.search.dao.SearchDAO;
import com.withcare.search.dto.SearchDTO;
import com.withcare.search.dto.SearchResultDTO;

@Service
public class SearchService {

    @Autowired SearchDAO dao;
    Logger log = LoggerFactory.getLogger(getClass());
    
    // 검색어 저장
    public int insertSearch(SearchDTO dto) {
    	try {
            // 검색어가 null이거나 비어있으면 저장하지 않음
            if (dto.getSch_keyword() == null || dto.getSch_keyword().trim().isEmpty()) {
                log.warn("빈 검색어 저장 시도가 무시됨");
                return 0;
            }
            
            log.info("검색어 저장 시도: {}", dto.getSch_keyword());
            int result = dao.insertSearch(dto);
            log.info("검색어 저장 결과: {}", result);
            return result;
        } catch (Exception e) {
            log.error("검색어 저장 중 오류 발생: {}", e.getMessage(), e);
            return 0;
        }

    }

    // 검색 결과 조회
    public List<SearchResultDTO> getSearchResult(SearchDTO dto) {
        return dao.getSearchResult(dto);
    }

    // 최근 검색어
	public List<SearchDTO> searchRecent(String sch_id) {
		return dao.searchRecent(sch_id);
	}

	// 최근 검색어 기반 추천 게시글
	public List<SearchResultDTO> recommendPost(String sch_id) {
		List<SearchDTO>recentKeyword = dao.searchRecent(sch_id);
		if (recentKeyword == null || recentKeyword.isEmpty()) {
			return new ArrayList<>(); // 빈 리스트 반환
		}
		// 가장 최근 검색어 
		String latestKeyword = recentKeyword.get(0).getSch_keyword();
		return dao.recommendPost(latestKeyword);
	}

	// 로그인 / 검색어 X (default 리스트)
	public List<SearchResultDTO> recommendDefault() {
		return dao.recommendDefault();
	}

	// 인기 검색어
	public List<Map<String, Object>> searchPopular() {
		return dao.searchPopular();
	}

	// 프로필 정보 기반 정보 게시판 게시글 불러오기 *board_idx 5, 6번
	public List<SearchResultDTO> searchCancer(ProfileDTO profileDTO) {
		String cancerKeyword = null;
		String stageKeyword = null;
		
		if (profileDTO.getCancer_idx() != 0) {
			cancerKeyword = profileDTO.getCancer_name();
		}
		if (profileDTO.getStage_idx() != 0) {
			stageKeyword = profileDTO.getStage_name();
		}
		
		log.info("▶ 사용자 프로필: 암 종류 = {}, 병기 = {}", profileDTO.getCancer_name(), profileDTO.getStage_name());

		return dao.searchCancer(cancerKeyword,stageKeyword);
	}

	// 프로필에서 암 종류 정보 가져오기
	public ProfileDTO profileCancer(String loginId) {
		return dao.profileCancer(loginId);
	}
   
	// 프로필에서 병기 정보 가져오기
	public boolean searchHistory(String loginId) {
		Integer cnt = dao.searchHistory(loginId);
		return cnt != null && cnt > 0;
	}

	public int getSearchResultCount(SearchDTO dto) {
        return dao.getSearchResultCount(dto);
    }
	

}
