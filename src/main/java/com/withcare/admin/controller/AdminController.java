package com.withcare.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.admin.dto.AdminMemberDTO;
import com.withcare.admin.service.AdminService;
import com.withcare.member.dto.MemberDTO;
import com.withcare.util.JwtToken.JwtUtils;

@CrossOrigin
@RestController
public class AdminController {
	
	@Autowired AdminService svc;
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	Map<String, Object> result = null;
	
	// 관리자 권한 부여
	@PutMapping("/admin/grant")
	public Map<String, Object>adminGrant(
			@RequestBody MemberDTO memberDTO,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		
		boolean login = false;
		boolean success = false;
		
		// 로그인 사용자 레벨 확인
		int user_lv = svc.userLevel(loginId);
		
		// 관리자 레벨 체크
		if (user_lv != 7) {
			result.put("success", false);
			return result;
		}
		
		// 로그인 유효성 체크
		if (loginId != null && !loginId.isEmpty()) {
			login = true;
			
			// 권한 부여 실행
			params.put("id", memberDTO.getId());
			params.put("lv_idx", memberDTO.getLv_idx());
			
			success = svc.levelUpdate(params);
		}
		
		// 결과 반환
		result.put("success", success);
		result.put("loginYN", login);
		
		return result;
	}
	
	@PostMapping("/admin/member/list")
	public List<AdminMemberDTO> adminMemberList(
	        @RequestBody(required = false) Map<String, Object> params) {
		
	    String searchId = (String) params.get("searchId");
		String sortField = (String) params.get("sortField");
		String sortOrder= (String) params.get("sortOrder");
		String blockFilter = (String) params.get("blockFilter");
		
	    int page = 1;
	    int size = 10;
		
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page").toString());
        }
	    
        if (params.get("size") != null) {
            size = Integer.parseInt(params.get("size").toString());
        }
        
		int start = (page - 1) * size;
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("searchId", searchId);
		result.put("start", start);
		result.put("size", size);
		result.put("sortField", sortField);
		result.put("sortOrder", sortOrder);
		result.put("blockFilter", blockFilter);
		
	    return svc.adminMemberList(result);
	}
	
}
