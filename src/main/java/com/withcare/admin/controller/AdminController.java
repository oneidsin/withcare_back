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
import com.withcare.admin.dto.AdminMemberDetailDTO;
import com.withcare.admin.service.AdminService;
import com.withcare.member.dto.MemberDTO;
import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;
import com.withcare.profile.dto.TimelineDTO;
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
	
	// 멤버 리스트 확인
	@PostMapping("/admin/member/list")
	public Map<String, Object> adminMemberList(
	        @RequestBody(required = false) Map<String, Object> params
	        ,@RequestHeader Map<String, String> header) {
		
		result = new HashMap<String, Object>();
		
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		
		boolean login = false;
		
		// 로그인 유효성 체크
		if (loginId == null || loginId.isEmpty()) {
			result.put("success", false);
			login = true;
			return result;
		}
		
		// 로그인 사용자 레벨 확인
		int user_lv = svc.userLevel(loginId);
		
		// 관리자 레벨 체크
		if (user_lv != 7) {
			result.put("success", false);
			return result;
		}
		
		// null 이면 가져오지 말아라
	    String searchId = (params != null) ? (String) params.get("searchId") : null;
		String sortField = (params != null) ? (String) params.get("sortField") : null;
		String sortOrder= (params != null) ? (String) params.get("sortOrder") : null;
		String blockFilter = (params != null) ? (String) params.get("blockFilter") : null;
		
		// page, size 초기화
	    int page = 1;
	    int size = 10;
		
	    // null 아닐때만 가져와라
        if (params != null && params.get("page") != null) {
            page = Integer.parseInt(params.get("page").toString());
        }
	    
        if (params != null && params.get("size") != null) {
            size = Integer.parseInt(params.get("size").toString());
        }
        
        // start 초기화
		int start = (page - 1) * size;
		
		result.put("searchId", searchId);
		result.put("start", start);
		result.put("size", size);
		result.put("sortField", sortField);
		result.put("sortOrder", sortOrder);
		result.put("blockFilter", blockFilter);
		
		List<AdminMemberDTO> memberList = svc.adminMemberList(result);
		
		result.put("success", true);
		result.put("data", memberList);
		result.put("loginId", loginId);
		
	    return result;
	}
	
	// 회원 정보 상세보기
	@PostMapping("/admin/member/detail")
	public Map<String, Object> adminMemberDetail(
			@RequestBody Map<String, String> param,
			@RequestHeader Map<String, String>header){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		
		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId)!=7) {
			result.put("success", false);
			return result;
		}
		
		String targetId = param.get("id");
		
		AdminMemberDetailDTO detail = svc.adminMemberDetail(targetId);
		
		result.put("success", true);
		result.put("data", detail);
		return result;
	}
	
	
	// 작성 게시글 목록 확인
	@PostMapping("/admin/member/post")
	public Map<String, Object> adminMemberPost(
			@RequestBody Map<String, String>param,
			@RequestHeader Map<String, String>header){
		
		Map<String, Object> result = new HashMap<String, Object>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		
		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId)!=7) {
			result.put("success", false);
			return result;
		}
		
		String targetId = param.get("id");
		List<PostDTO> posts = svc.adminMemberPost(targetId);
		
		result.put("success", true);
		result.put("data", posts);
		
		return result;
	}
	
	// 댓글 + 멘션 목록 조회
	@PostMapping("/admin/member/comments")
	public Map<String, Object> adminMemberComments(
			@RequestBody Map<String, String>param,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		
		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId)!=7) {
			result.put("success", false);
			return result;
		}
		
		String targetId = param.get("id");
		
		List<Map<String, Object>> commentList = svc.adminMemberComments(targetId);
		
		result.put("success", true);
		result.put("data", commentList);
		
		return result;
	}
	
	// 추천 누른 게시글 (비추천 X)
	@PostMapping("/admin/member/like")
	public Map<String, Object>adminMemberLike(
			@RequestBody Map<String, String>param,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		
		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId)!=7) {
			result.put("success", false);
			return result;
		}
		
		String targetId = param.get("id");
		
		List<LikeDislikeDTO> likes = svc.adminMemberLike(targetId);
		result.put("success", true);
		result.put("data", likes);
		
		return result;
	}
	
	@PostMapping("/admin/member/timelines")
	public Map<String, Object>adminMemberTimeline(
			@RequestBody Map<String, String>param,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		
		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId)!=7) {
			result.put("success", false);
			return result;
		}
		
		String targetId = param.get("id");
		
		List<TimelineDTO> timeline = svc.adminMemberTimeline(targetId);
		result.put("success", true);
		result.put("data", timeline);
		
		return result;
	}
	
	/* 배지 추가
	@PostMapping("/admin/bdg/add")
	public Map<String, Object>adminBdgAdd(
			@RequestBody BadgeDTO bdg,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		
		if (loginId == null || loginId.isEmpty() || svc.userLevel(loginId)!=7) {
			result.put("success", false);
			return result;
		}
		
		boolean updated = svc.adminBdgAdd(bdg);
		result.put("success", true);
		
		return result;
	}
	*/
	
	// 배지 수정
	
	// 배지 삭제
	
	// 레벨 조건 추가
	
	// 레벨 조건 수정
	
	// 레벨 조건 삭제
	
	
	
	
	
	
	
	
	
	
	
	
}
