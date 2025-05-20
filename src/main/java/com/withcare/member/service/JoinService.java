package com.withcare.member.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.member.dao.JoinDAO;
import com.withcare.profile.dao.ProfileDAO;


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
			p_dao.insertProfile(params.get("id")); // 프로필 생성
			return true;
		}
		
		return false;
	}


}
