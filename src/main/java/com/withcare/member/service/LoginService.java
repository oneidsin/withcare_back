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
		
		// 사용자 정보(id, pw, user_del_yn, block_yn) 조회
	    Map<String, Object> member = dao.getLoginInfo(params);

	    // 1. 아이디가 존재하지 않는 경우
	    if (member == null) {
	        result.put("success", false);
	        result.put("msg", "존재하지 않는 아이디입니다.");
	        return result;
	    }

	    // 2. 탈퇴한 회원인 경우
	    if ("1".equals(String.valueOf(member.get("user_del_yn")))) {
	        result.put("success", false);
	        result.put("msg", "탈퇴한 회원입니다.");
	        return result;
	    }
	    
	    // 3. 차단된 회원인 경우
	    if ("1".equals(String.valueOf(member.get("block_yn")))) {
	        result.put("success", false);
	        result.put("msg", "차단된 회원입니다.");
	        return result;
	    }

	    // 4. 비밀번호 불일치
	    String inputPw = params.get("pw");
	    String actualPw = String.valueOf(member.get("pw"));

	    if (!actualPw.equals(inputPw)) {
	        result.put("success", false);
	        result.put("msg", "비밀번호가 일치하지 않습니다.");
	        return result;
	    }

	
		 // 5. 로그인 성공 - 방문수 & 접속일 업데이트
		 dao.visitCnt(params.get("id")); // 방문수 증가
		 dao.updateAccessDate(params.get("id")); // 접속 일자 업데이트
	
		 result.put("success", true);
		 result.put("msg", "로그인 성공");
	
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
	    return dao.getLvIdx(id); // DAO에서 쿼리 날리기
	}


}
