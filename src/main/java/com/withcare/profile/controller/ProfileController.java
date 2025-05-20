package com.withcare.profile.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.profile.dto.ProfileDTO;
import com.withcare.profile.service.ProfileService;
import com.withcare.util.JwtToken;

@CrossOrigin
@RestController
public class ProfileController {

	@Autowired
	ProfileService svc;

	Map<String, Object> result = null;

	Logger log = LoggerFactory.getLogger(getClass());

	/*
	 * // 프로필 저장 post
	 * 
	 * @PostMapping("/profile") public Map<String, Object> saveProfile(@RequestBody
	 * Map<String, Object> pro) { result = new HashMap<String, Object>();
	 * 
	 * try { boolean success = svc.saveProfile(pro); result.put("success", success);
	 * result.put("message", success ? "프로필 저장 완료" : "프로필 저장 실패"); } catch
	 * (Exception e) { log.error("[오류] 프로필 저장 중 예외 발생", e); result.put("success",
	 * false); result.put("message", "서버 오류"); } return result; }
	 */

	// 프로필 열람 get
	@GetMapping("/profile/{id}")
	public Map<String, Object> getProfile(
	        @PathVariable("id") String id,
	        HttpServletRequest header) {

	    Map<String, Object> result = new HashMap<>();

	    try {
	        // 1. Authorization 헤더에서 토큰 추출
	        String authHeader = header.getHeader("Authorization");
	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            result.put("status", "fail");
	            result.put("message", "인증 토큰이 필요합니다.");
	            return result;
	        }

	        String token = authHeader.substring(7); // "Bearer " 제거

	        // 2. 토큰에서 사용자 정보 추출
	        Map<String, Object> claims = JwtToken.JwtUtils.readToken(token);
	        String tokenId = (String) claims.get("id");

	        if (tokenId == null || tokenId.isEmpty()) {
	            result.put("status", "fail");
	            result.put("message", "유효하지 않은 토큰입니다.");
	            return result;
	        }

	        // 3. 프로필 조회 로직
	        ProfileDTO dto = svc.getProfile(id);

	        if (dto != null) {
	            result.put("status", "success");
	            result.put("data", dto);
	        } else {
	            result.put("status", "fail");
	            result.put("message", "프로필을 찾을 수 없습니다.");
	        }

	    } catch (Exception e) {
	        result.put("status", "error");
	        result.put("message", "서버 오류: " + e.getMessage());
	    }

	    return result;
	}

	// 회원 개인 정보 수정 기능 put
	@PutMapping("/profile/update")
	public Map<String, Object> updateProfile(
	    @RequestBody ProfileDTO dto,
	    @RequestHeader("Authorization") String header
	) {
	    Map<String, Object> result = new HashMap<>();

	    try {
	        // 1. 토큰에서 ID 추출
	        if (header == null || !header.startsWith("Bearer")) {
	            result.put("status", "fail");
	            result.put("message", "토큰이 없습니다.");
	            return result;
	        }

	        String token = header.substring(7);
	        Map<String, Object> claims = JwtToken.JwtUtils.readToken(token);
	        String tokenId = (String) claims.get("id");

	        if (tokenId == null || tokenId.isEmpty()) {
	            result.put("status", "fail");
	            result.put("message", "유효하지 않은 토큰입니다.");
	            return result;
	        }

	        // 2. 토큰에서 추출한 ID로 수정
	        dto.setId(tokenId);
	        int updated = svc.updateProfile(dto);
	        result.put("status", updated > 0 ? "success" : "fail");

	    } catch (Exception e) {
	        result.put("status", "error");
	        result.put("message", e.getMessage());
	    }

	    return result;
	}

	// 타인이 프로필 확인하는 기능 get
	@GetMapping("/profile/view/{id}")
	public Map<String, Object> viewOtherProfile(
	    @PathVariable("id") String id,
	    @RequestHeader(value = "Authorization", required = false) String header
	) {
	    Map<String, Object> result = new HashMap<>();

	    try {
	        // 1. 토큰 유무 확인
	        if (header == null || !header.startsWith("Bearer ")) {
	            result.put("status", "fail");
	            result.put("message", "인증 토큰이 필요합니다.");
	            return result;
	        }

	        // 2. 토큰에서 사용자 정보 추출
	        String token = header.substring(7); // "Bearer " 제거
	        Map<String, Object> claims = JwtToken.JwtUtils.readToken(token);
	        String tokenId = (String) claims.get("id");

	        if (tokenId == null || tokenId.isEmpty()) {
	            result.put("status", "fail");
	            result.put("message", "유효하지 않은 토큰입니다.");
	            return result;
	        }

	        // 3. 타인의 프로필 조회
	        ProfileDTO dto = svc.getProfileById(id);
	        if (dto != null) {
	            result.put("status", "success");
	            result.put("profile", dto);
	        } else {
	            result.put("status", "not_found");
	        }

	    } catch (Exception e) {
	        result.put("status", "error");
	        result.put("message", e.getMessage());
	    }

	    return result;
	}

}
