package com.withcare.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.member.service.MemberService;
import com.withcare.util.JwtToken.JwtUtils;

@CrossOrigin
@RestController
public class MemberController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	Map<String, Object> result = null;
	
	@Autowired MemberService svc;
	
	// 회원 탈퇴
	
	@PutMapping("/delete/{id}")
	public Map<String, Object> delete(@PathVariable String id, @RequestHeader("Authorization") String header) {
	    log.info("회원 탈퇴 요청: " + id);

	    result = new HashMap<>();

	    String token = null;
	    if (header != null && header.startsWith("Bearer ")) {
	        token = header.substring(7);
	    } else {
	        result.put("success", false);
	        result.put("msg", "토큰이 없거나 형식이 잘못되었습니다.");
	        return result;
	    }

	    Map<String, Object> claims = JwtUtils.readToken(token);

	    if (!id.equals(claims.get("id"))) {
	        result.put("success", false);
	        result.put("msg", "권한이 없습니다.");
	        return result;
	    }

	    boolean success = svc.delete(id);
	    result.put("success", success);

	    return result;
	}

}
