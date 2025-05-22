package com.withcare.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.post.dto.PostDTO;
import com.withcare.profile.dto.CancerDTO;
import com.withcare.profile.dto.ProfileDTO;
import com.withcare.profile.dto.StageDTO;
import com.withcare.search.dao.SearchDAO;
import com.withcare.search.dto.SearchResultDTO;
import com.withcare.search.dto.SearchDTO;

@Service
public class SearchService {

    @Autowired SearchDAO dao;
    Logger log = LoggerFactory.getLogger(getClass());
    
    // 검색어 저장
    public int insertSearch(SearchDTO dto) {
    	// 72 시간 이내에 같은 키워드 있는지 확인
    	int exists = dao.recentKeyword(dto.getSch_id(), dto.getSch_keyword());
    	
    	if (exists==0) {
       	 // 1. 게시글 하나 조회해서 board_idx 가져오기
            Integer boardIdx = dao.findBoardIdxForSearch(dto);
            
            // board 테이블에 실제 존재하는 board_idx인지 확인
            if (boardIdx != null && boardIdx != 0) {
                dto.setBoard_idx(boardIdx);
            } else {
                dto.setBoard_idx(0); // 또는 예외 처리
            }
            // 2. search 테이블에 저장
            return dao.insertSearch(dto);
            
		}else {
			// 중복 있을 때는 저장하지 않고 0 반환
			return 0;
		}

    }

    // 검색 결과 조회
    public List<SearchResultDTO> getSearchResult(SearchDTO dto) {
        return dao.getSearchResult(dto);
    }

	public List<SearchDTO> searchRecent(String sch_id) {
		return dao.searchRecent(sch_id);
	}

	public List<SearchResultDTO> recommendPost(String sch_id) {
		List<SearchDTO>recentKeyword = dao.searchRecent(sch_id);
		if (recentKeyword == null || recentKeyword.isEmpty()) {
			return new ArrayList<>(); // 빈 리스트 반환
		}
		// 가장 최근 검색어 
		String latestKeyword = recentKeyword.get(0).getSch_keyword();
		return dao.recommendPost(latestKeyword);
	}

	public List<SearchResultDTO> recommendDefault() {
		return dao.recommendDefault();
	}

	public List<Map<String, Object>> searchPopular() {
		return dao.searchPopular();
	}

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

	public ProfileDTO profileCancer(String loginId) {
		return dao.profileCancer(loginId);
	}
   
	
	public boolean searchHistory(String loginId) {
		Integer cnt = dao.searchHistory(loginId);
		return cnt != null && cnt > 0;
	}

}
