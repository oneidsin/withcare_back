package com.withcare.member.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.member.dao.LoginDAO;


@Service
public class LoginService {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	Map<String, Object> result = null;
	
	@Autowired LoginDAO dao;

	public Map<String, Object> login(Map<String, String> params) {
	    result = new HashMap<>();

	    try {
	        // 1. 먼저 회원 상태와 비밀번호 체크
	        Map<String, Object> memberStatus = dao.checkMemberStatus(params);
	        
	        // 회원이 존재하지 않는 경우
	        if (memberStatus == null) {
	            result.put("success", false);
	            result.put("msg", "존재하지 않는 아이디입니다.");
	            return result;
	        }

	        // 비밀번호 체크
	        String inputPw = params.get("pw");
	        String actualPw = String.valueOf(memberStatus.get("pw"));
	        if (!actualPw.equals(inputPw)) {
	            result.put("success", false);
	            result.put("msg", "비밀번호가 일치하지 않습니다.");
	            return result;
	        }

	        // 탈퇴한 회원인 경우
	        if ("1".equals(String.valueOf(memberStatus.get("user_del_yn")))) {
	            result.put("success", false);
	            result.put("msg", "탈퇴한 회원입니다.");
	            return result;
	        }
	        
	        // 차단된 회원인 경우
	        if ("1".equals(String.valueOf(memberStatus.get("block_yn")))) {
	            result.put("success", false);
	            result.put("msg", "차단된 회원입니다.");
	            return result;
	        }

	        // 2. 정상 회원인 경우 로그인 처리 (이 쿼리에는 user_del_yn = 0 AND block_yn = 0 조건이 있음)
	        Map<String, Object> loginResult = dao.login(params);
	        
	        // 로그인 실패 (추가 안전장치)
	        if (loginResult == null) {
	            result.put("success", false);
	            result.put("msg", "탈퇴하거나 차단된 회원입니다.");
	            return result;
	        }

	        // 로그인 성공 - 방문수 & 접속일 업데이트
	        dao.visitCnt(params.get("id")); // 방문수 증가
	        dao.updateAccessDate(params.get("id")); // 접속 일자 업데이트

	        result.put("success", true);
	        result.put("msg", "로그인 성공");
	        result.put("id", memberStatus.get("id"));
	        
	    } catch (Exception e) {
	        log.error("로그인 처리 중 오류 발생", e);
	        result.put("success", false);
	        result.put("msg", "로그인 처리 중 오류가 발생했습니다.");
	    }

	    return result;
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

	public int getLvIdx(String id) {
		return dao.getLvIdx(id);
	}


}
