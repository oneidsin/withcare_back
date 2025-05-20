package com.withcare.member.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.member.dao.LoginDAO;
import com.withcare.profile.dao.ProfileDAO;


@Service
public class LoginService {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired LoginDAO dao;

	public boolean login(Map<String, String> params) {
		int cnt = dao.login(params);
		if(cnt > 0) {
	        dao.visitCnt(params.get("id")); // 로그인 시 방문자 수 저장
	        return true;
	    }
	    return false;
	}

	public String findId(String name, String year, String email) {
		return dao.findId(name, year, email);
	}

	public String findPw(String id, String name, String year, String email) {
		return dao.findPw(id, name, year, email);
	}

	public boolean resetPw(String id, String newPw) {
		log.info("resetPw 서비스 호출 - id: {}, newPw: {}", id, newPw);
	    Map<String, String> map = new HashMap<>();
	    map.put("id", id);
	    map.put("newPw", newPw);
	    int updated = dao.updatePw(map);
	    return updated > 0;
	}


}
