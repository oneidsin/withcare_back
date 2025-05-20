package com.withcare.profile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.profile.dto.TimelineDTO;
import com.withcare.profile.service.TimelineService;
import com.withcare.util.JwtToken;
import com.withcare.util.JwtToken.JwtUtils;

@RestController
public class TimelineController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	TimelineService svc;

	// 토큰에서 사용자 아이디 추출
	private String get_token(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			return (String) JwtUtils.readToken(token).get("id");
		}
		return null;
	}

	// 타임라인 작성
	@PostMapping("/timeline/write")
	public Map<String, Object> writeTimeline(@RequestBody TimelineDTO dto,
			@RequestHeader("Authorization") String authorizationHeader) {

		String loginId = get_token(authorizationHeader);
		log.info("사용자 아이디: {}", loginId);

		dto.setTime_user_id(loginId);

		svc.writeTimeline(dto);

		Map<String, Object> result = new HashMap<>();
		result.put("status", "success");
		result.put("msg", "타임라인 등록이 완료 되었습니다.");

		return result;
	}

	// 타임라인 수정
	@PutMapping("/timeline/update")
	public ResponseEntity<String> updateTimeline(@RequestBody TimelineDTO dto,
			@RequestHeader("Authorization") String authorizationHeader) {

		String loginId = get_token(authorizationHeader);
		log.info("사용자 아이디: {}", loginId);

		dto.setTime_user_id(loginId);
		svc.updateTimeline(dto);

		return ResponseEntity.ok("updated");
	}

	// 타임라인 삭제
	@DeleteMapping("/timeline/delete")
	public ResponseEntity<String> delTimeline(@RequestBody TimelineDTO dto,
			@RequestHeader("Authorization") String authorizationHeader) {

		String loginId = get_token(authorizationHeader);
		log.info("사용자 아이디: {}", loginId);

		boolean deleted = svc.delTimeline(dto.getTime_idx(), loginId);
		if (deleted) {
			return ResponseEntity.ok("deleted");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
		}
	}

	// 유저의 타임라인 리스트
	@GetMapping("/timeline/list")
	public ResponseEntity<?> timelineList(@RequestHeader("Authorization") String authorizationHeader) {
		
		String id = get_token(authorizationHeader);

		if (id == null || id.isEmpty()) {
			return ResponseEntity.status(401).body("로그인을 진행해주세요.");
		}

		Map<String, List<TimelineDTO>> t_list_by_year = svc.timelineList(id);

		return ResponseEntity.ok(t_list_by_year);

	}

	// 타 유저의 공개 타임라인 리스트
	@GetMapping("/timeline/public/{id}") // {id}는 프로필 주인 id
	public ResponseEntity<?> publicList(@PathVariable String id, @RequestHeader("Authorization") String auth_header) {

		String loginId = get_token(auth_header);

		List<TimelineDTO> public_timeline = svc.publicList(id);

		List<Map<String, Object>> result = public_timeline.stream().map(t -> {
			Map<String, Object> map = new HashMap<>();
			map.put("time_idx", t.getTime_idx());
			map.put("time_user_id", t.getTime_user_id());
			map.put("day", t.getDay());
			map.put("time_title", t.getTime_title());
			map.put("time_content", t.getTime_content());
			map.put("time_public_yn", t.getTime_public_yn());
			map.put("time_update_date", t.getTime_update_date());
			map.put("isMine", t.getTime_user_id().equals(id)); // 내가 쓴 글인지 구분
			return map;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(result);
	}

}
