package com.withcare.profile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.withcare.comment.dto.ComDTO;
import com.withcare.comment.dto.MenDTO;
import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;
import com.withcare.profile.dto.ProfileDTO;
import com.withcare.profile.service.ProfileService;
import com.withcare.search.dto.SearchDTO;
import com.withcare.util.JwtToken.JwtUtils;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {
	RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS
})
public class ProfileController {

	@Autowired
	ProfileService svc;

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

	// 프로필 열람
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> getProfile(@PathVariable("id") String id) {
		try {
			ProfileDTO profile = svc.getProfile(id);
			if (profile != null) {
				Map<String, Object> response = new HashMap<>();
				response.put("status", "success");
				response.put("data", profile);
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("status", "error", "message", "프로필을 찾을 수 없습니다."));
			}
		} catch (Exception e) {
			log.error("프로필 조회 중 오류 발생", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("status", "error", "message", "서버 오류가 발생했습니다."));
		}
	}

	// 프로필 수정
	@PutMapping("/profile/update")
	public ResponseEntity<?> updateProfile(@RequestBody ProfileDTO dto) {
		try {
			int result = svc.updateProfile(dto);
			if (result > 0) {
				return ResponseEntity.ok(Map.of("status", "success", "message", "프로필이 성공적으로 수정되었습니다."));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(Map.of("status", "error", "message", "프로필 수정에 실패했습니다."));
			}
		} catch (Exception e) {
			log.error("프로필 수정 중 오류 발생", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("status", "error", "message", "서버 오류가 발생했습니다."));
		}
	}

	// 프로필 이미지 업로드
	@PostMapping("/profile/upload")
	public ResponseEntity<?> uploadProfileImage(@RequestParam("file") MultipartFile file) {
		try {
			String imageUrl = svc.saveProfileImage(file);
			return ResponseEntity.ok(Map.of("status", "success", "url", imageUrl));
		} catch (Exception e) {
			log.error("프로필 이미지 업로드 중 오류 발생", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("status", "error", "message", "이미지 업로드에 실패했습니다."));
		}
	}

	// 타인 프로필 조회
	@GetMapping("/profile/view/{id}")
	public ResponseEntity<?> viewOtherProfile(
			@PathVariable("id") String id,
			@RequestHeader(value = "Authorization", required = false) String token) {

		try {
			if (token == null || token.isBlank()) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(Map.of("status", "fail", "message", "로그인이 필요합니다."));
			}

			Map<String, Object> payload = JwtUtils.readToken(token);
			if (payload == null || !payload.containsKey("id")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(Map.of("status", "fail", "message", "유효하지 않은 토큰입니다."));
			}

			Map<String, Object> result = new HashMap<>();
			ProfileDTO profile = svc.getProfileById(id);

			if (profile == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("status", "fail", "message", "프로필을 찾을 수 없습니다."));
			}

			// 프로필이 비공개인 경우
			if (!profile.isProfile_yn() && !id.equals(payload.get("id"))) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(Map.of("status", "fail", "message", "비공개 프로필입니다."));
			}

			result.put("status", "success");
			result.put("profile", profile);
			result.put("posts", svc.getUserPosts(id));
			result.put("comments", svc.getUserComments(id));
			result.put("likes", svc.getUserLikes(id));
			result.put("searches", svc.getUserSearches(id));

			return ResponseEntity.ok(result);

		} catch (Exception e) {
			log.error("타인 프로필 조회 중 오류 발생", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("status", "error", "message", "서버 오류가 발생했습니다."));
		}
	}

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

			// 3. 해당 사용자 활동 정보 조회
			List<PostDTO> posts = svc.getUserPosts(id);
			List<ComDTO> comments = svc.getUserComments(id);
			List<LikeDislikeDTO> likes = svc.getUserLikes(id);
			List<SearchDTO> searches = svc.getUserSearches(id);
			List<MenDTO> mentions = svc.getUserMentions(id);

			// 4. 응답 조립
			result.put("status", "success");
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
