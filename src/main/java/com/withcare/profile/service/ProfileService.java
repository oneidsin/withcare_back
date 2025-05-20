package com.withcare.profile.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.profile.dao.ProfileDAO;
import com.withcare.profile.dto.ProfileDTO;

@Service
public class ProfileService {

	@Autowired ProfileDAO dao;
	
	Logger log = LoggerFactory.getLogger(getClass());

	/*
	 * public boolean saveProfile(Map<String, Object> map) { return
	 * dao.saveProfile(map) > 0; }
	 */

	// 프로필 보기
	 public ProfileDTO getProfile(String id) {
	        return dao.getProfileById(id);
	}

	 // 프로필 수정
	 public int updateProfile(ProfileDTO dto) {
	        return dao.updateProfile(dto);
}

	 // 타인 프로필 보기
	public ProfileDTO getProfileById(String id) {
		return dao.getProfileById(id);
	}
	
}
