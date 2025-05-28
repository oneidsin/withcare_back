package com.withcare.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.member.service.LoginService;
import com.withcare.util.JwtToken.JwtUtils;

@CrossOrigin
@RestController
public class LoginController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	Map<String, Object> result = null;
	
	@Autowired LoginService svc;
	
	// 로그인
	
	@PostMapping(value="/login")
	public Map<String, Object> login(@RequestBody Map<String, String> info) {
		Map<String, Object> result = new HashMap<>();
		
		try {
			boolean success = svc.login(info);
			
			if (success) {
				String id = info.get("id");
				String token = JwtUtils.setToken("id", id);
				result.put("success", true);
				result.put("token", token);
				result.put("id", id);
			} else {
				result.put("success", false);
				result.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
			}
		} catch (Exception e) {
			log.error("로그인 처리 중 오류 발생", e);
			result.put("success", false);
			result.put("message", "서버 오류가 발생했습니다.");
		}
		
		return result;
	}
	
	// 로그아웃
	
	@PostMapping("/logout") 
	public Map<String, Object> logout() {
	    Map<String, Object> result = new HashMap<>();
	    
	    result.put("success", true);
	    result.put("message", "로그아웃 되었습니다.");
	    
	    return result;
	}
	
	// 아이디 찾기
	
	@GetMapping("/find-id")
    public ResponseEntity<String> findId(
            @RequestParam String name,
            @RequestParam String year,
            @RequestParam String email) {

        String userId = svc.findId(name, year, email);

        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 아이디가 없습니다.");
        }
    }
	
	// 비밀번호 찾기
	
	@GetMapping("/find-pw")
	public ResponseEntity<String> findPw(
	        @RequestParam String id,
	        @RequestParam String name,
	        @RequestParam String year,
	        @RequestParam String email) {

	    String userPw = svc.findPw(id, name, year, email);

	    if (userPw != null) {
	        return ResponseEntity.ok(userPw);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 비밀번호가 없습니다.");
	    }
	}
	
	// 비밀번호 재설정

	@PutMapping("/reset-pw")
	public ResponseEntity<String> resetPw(@RequestBody Map<String, String> params) {
		
		log.info("resetPw 컨트롤러 진입, params: {}", params);
		
	    String id = params.get("id");
	    String newPw = params.get("newPw");

	    boolean success = svc.resetPw(id, newPw);

	    if (success) {
	        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경에 실패했습니다.");
	    }
	}

	
	@GetMapping("/member/info")
	public Map<String, Object> getMemberInfo(
			@RequestHeader Map<String, String> header) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        String id = (String) JwtUtils.readToken(header.get("authorization")).get("id");
	        int lvIdx = svc.getLvIdx(id); // LoginService에서 회원 레벨 가져오는 메서드 필요
	        result.put("id", id);
	        result.put("lv_idx", lvIdx);
	        result.put("success", true);
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", "유효하지 않은 토큰");
	    }
	    return result;
	}
	

}
