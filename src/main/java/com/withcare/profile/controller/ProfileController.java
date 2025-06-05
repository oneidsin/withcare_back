package com.withcare.profile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.withcare.comment.dto.ComDTO;
import com.withcare.comment.dto.MenDTO;
import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;
import com.withcare.profile.dto.ProfileDTO;
import com.withcare.profile.service.ProfileService;
import com.withcare.search.dto.SearchDTO;
import com.withcare.util.JwtToken.JwtUtils;




@CrossOrigin
@RestController
public class ProfileController {

	
	 @Value("${file.upload-dir}")
	   private String uploadDir;
	 
	 
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
	public Map<String, Object> getProfile(@PathVariable("id") String id, @RequestHeader("authorization") Map<String, String> header) {

	    Map<String, Object> result = new HashMap<>();
	    log.info(id);
	    log.info("header : "+header);
	    try {
	        String token = header.get("authorization");
	        log.info(token);
	        Map<String, Object> payload = JwtUtils.readToken(token);
	        String loginId = (String) payload.get("id");

	        if (loginId != null && loginId.equals(id)) {
	            ProfileDTO dto = svc.getProfileById(id);
	            
	            // null 값을 0으로 변환하여 JSON 직렬화 문제 해결
	            if (dto != null) {
	                if (dto.getCancer_idx() == null) {
	                    dto.setCancer_idx(0);
	                }
	                if (dto.getStage_idx() == null) {
	                    dto.setStage_idx(0);
	                }
	            }
	            
	            result.put("status", "success");
	            result.put("data", dto);
	        } else {
	            result.put("status", "fail");
	            result.put("message", "인증된 사용자만 접근할 수 있습니다.");
	        }

	    } catch (Exception e) {
	        log.error("프로필 조회 오류", e);
	        result.put("status", "error");
	        result.put("message", "서버 오류: " + e.getMessage());
	    }

	    return result;
	}
	
	// 프로필 수정
	@PutMapping("/profile/update")
	public ResponseEntity<?> updateProfile(
	        @RequestPart("info") ProfileDTO dto,
	        @RequestPart(value = "profile_image", required = false) MultipartFile file,
	        @RequestHeader("Authorization") String token) {
		
			log.info("token : "+token);
			
	    try {
	        // JWT에서 ID 추출
	        String tokenId = (String) JwtUtils.readToken(token).get("id");
	        if (tokenId == null || tokenId.isBlank()) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                                 .body(Map.of("status", "fail", "message", "잘못된 토큰입니다."));
	        }

	        dto.setId(tokenId);

	        // 프로필 이미지 저장 처리
	        if (file != null && !file.isEmpty()) {
	            String savedPath = svc.saveProfileImage(file);
	            dto.setProfile_photo(savedPath);
	        }

	        // DB 업데이트
	        boolean success = svc.updateProfile(dto) > 0;
	        return ResponseEntity.ok(Map.of("status", success ? "success" : "fail"));

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Map.of("status", "error", "message", e.getMessage()));
	    }
	}


	// 타인이 프로필 확인하는 기능 get
	@GetMapping("/profile/view/{id}")
	public Map<String, Object> viewOtherProfile(@PathVariable("id") String id,
			@RequestHeader(value = "authorization", required = false) String token) {

		Map<String, Object> result = new HashMap<>();

		try {
			// 1. 토큰 없으면 로그인 필요
			if (token == null || token.trim().isEmpty()) {
				result.put("status", "fail");
				result.put("message", "로그인이 필요합니다.");
				return result;
			}

			// 2. 토큰 유효성 검사 및 ID 추출
			Map<String, Object> payload = JwtUtils.readToken(token);
			if (payload == null || !payload.containsKey("id") || payload.get("id") == null) {
				result.put("status", "fail");
				result.put("message", "유효하지 않은 토큰입니다.");
				return result;
			}

			// 3. 프로필 기본 정보 조회
			ProfileDTO profile = svc.getProfileById(id);

			// 4. 활동 정보 조회
			List<PostDTO> posts = svc.getUserPosts(id);
			List<ComDTO> comments = svc.getUserComments(id);
			List<LikeDislikeDTO> likes = svc.getUserLikes(id);
			List<SearchDTO> searches = svc.getUserSearches(id);
			List<MenDTO> mentions = svc.getUserMentions(id);

			// 5. 응답 조립
			result.put("status", "success");
			result.put("profile", profile);
			result.put("posts", posts);
			result.put("comments", comments);
			result.put("likes", likes);
			result.put("searches", searches);
			result.put("mentions", mentions);

		} catch (Exception e) {
			result.put("status", "error");
			result.put("message", "서버 오류: " + e.getMessage());
		}

		return result;
	}

	// 프로필 활동내역
	@GetMapping("/profile/activity/{id}")
	public Map<String, Object> getUserActivity(@PathVariable("id") String id,
	        @RequestHeader Map<String, String> header) {

	    Map<String, Object> result = new HashMap<>();

	    try {
	        // 1. 토큰 추출 및 검증
	        String token = header.get("authorization");
	        if (token == null || token.trim().isEmpty()) {
	            result.put("status", "fail");
	            result.put("message", "로그인이 필요합니다.");
	            return result;
	        }

	        // 2. 토큰 유효성 검사만 (사용자 ID 비교는 하지 않음)
	        JwtUtils.readToken(token); // 예외 발생 시 catch로 이동

	        // 3. 해당 사용자 활동 정보 조회 (기존 로직)
	        List<PostDTO> posts = svc.getUserPosts(id);
	        List<ComDTO> comments = svc.getUserComments(id);
	        List<LikeDislikeDTO> likes = svc.getUserLikes(id);
	        List<SearchDTO> searches = svc.getUserSearches(id);
	        List<MenDTO> mentions = svc.getUserMentions(id);

	        // 📌 NEW: 전체 개수 정보 조회 (레벨 시스템용)
	        int postCount = svc.getUserPostCount(id);
	        int commentCount = svc.getUserCommentCount(id);
	        int likeCount = svc.getUserLikeCount(id);
	        int timelineCount = svc.getUserTimelineCount(id);

	        // 4. 응답 조립
	        result.put("status", "success");
	        
	        // 기존 활동 내역 (최근 5개씩)
	        result.put("posts", posts);
	        result.put("comments", comments);
	        result.put("likes", likes);
	        result.put("searches", searches);
	        result.put("mentions", mentions);
	        
	        // 🎯 NEW: 전체 개수 정보 (레벨 계산용)
	        result.put("postCount", postCount);
	        result.put("commentCount", commentCount);
	        result.put("likeCount", likeCount);
	        result.put("timelineCount", timelineCount);

	    } catch (Exception e) {
	        result.put("status", "error");
	        result.put("message", "서버 오류: " + e.getMessage());
	    }

	    return result;
	}
	
	// ProfileController.java의 getPublicProfile 메서드 수정

@GetMapping("/profile/public/{id}")
public Map<String, Object> getPublicProfile(@PathVariable("id") String id) {
    Map<String, Object> result = new HashMap<>();
    try {
        // 토큰 검증 없이 바로 데이터 조회
        ProfileDTO profile = svc.getProfileById(id);
        
        // 레벨 정보 조회
        try {
            int userLvIdx = svc.getUserLvIdx(id);
            Map<String, Object> levelInfo = new HashMap<>();
            levelInfo.put("lv_idx", userLvIdx);
            result.put("levelInfo", levelInfo);
        } catch (Exception levelError) {
            log.warn("레벨 정보 조회 실패 for user {}: {}", id, levelError.getMessage());
        }
        
        // 배지 정보 조회 (기존 DTO 활용)
        try {
            List<Map<String, Object>> badges = svc.getPublicUserBadges(id);
            result.put("badges", badges);
            result.put("badgeCount", badges.size());
            
            // 메인 배지 정보 별도 추출
            Map<String, Object> mainBadge = badges.stream()
                .filter(badge -> {
                    Object bdgSymYn = badge.get("bdg_sym_yn");
                    return bdgSymYn != null && 
                           (bdgSymYn.equals(1) || bdgSymYn.equals(true) || "1".equals(bdgSymYn.toString()));
                })
                .findFirst()
                .orElse(null);
            result.put("mainBadge", mainBadge);
            
        } catch (Exception badgeError) {
            log.warn("배지 정보 조회 실패 for user {}: {}", id, badgeError.getMessage());
            result.put("badges", new ArrayList<>());
            result.put("badgeCount", 0);
            result.put("mainBadge", null);
        }
        
        // 활동 정보 조회
        List<PostDTO> posts = svc.getUserPosts(id);
        List<ComDTO> comments = svc.getUserComments(id);
        List<LikeDislikeDTO> likes = svc.getUserLikes(id);
        List<SearchDTO> searches = svc.getUserSearches(id);
        List<MenDTO> mentions = svc.getUserMentions(id);
        
        result.put("status", "success");
        result.put("profile", profile);
        result.put("posts", posts);
        result.put("comments", comments);
        result.put("likes", likes);
        result.put("searches", searches);
        result.put("mentions", mentions);
        
    } catch (Exception e) {
        result.put("status", "error");
        result.put("message", "서버 오류: " + e.getMessage());
    }
    return result;
}

}
