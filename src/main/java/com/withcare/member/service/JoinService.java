package com.withcare.member.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.member.dao.JoinDAO;
import com.withcare.profile.dao.ProfileDAO;
import com.withcare.profile.dto.CancerDTO;
import com.withcare.profile.dto.StageDTO;
import com.withcare.profile.dto.ProfileDTO;


@Service
public class JoinService {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired JoinDAO dao;
	@Autowired ProfileDAO p_dao;
	
	// 중복 체크

	public boolean overlay(String id) {
		
		int cnt = dao.overlay(id);
		
		return cnt == 0;
	}

	// 회원 가입
	
	public boolean join(Map<String, String> params) {
		int row = dao.join(params);
		
		if (row > 0) {
			// 기본 프로필 생성
			p_dao.insertProfile(params.get("id"));
			
			// cancer와 stage가 있다면 프로필 업데이트
			if (params.containsKey("cancer") && params.containsKey("stage")) {
				ProfileDTO profileDTO = new ProfileDTO();
				profileDTO.setId(params.get("id"));
				profileDTO.setCancer_idx(Integer.parseInt(params.get("cancer")));
				profileDTO.setStage_idx(Integer.parseInt(params.get("stage")));
				profileDTO.setProfile_yn(false);
				p_dao.updateProfile(profileDTO);
			}
			return true;
		}
		
		return false;
	}

	public List<CancerDTO> cancer() {
		return dao.cancer();
	}

	public List<StageDTO> stage() {
		return dao.stage();
	}


}
