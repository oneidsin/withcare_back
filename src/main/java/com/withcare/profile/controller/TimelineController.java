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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.withcare.util.JwtToken.JwtUtils;

@CrossOrigin
@RestController
public class TimelineController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired TimelineService svc;

	// WRITE TIMELINE
	@PostMapping("/timeline/write")
	public Map<String, Object> writeTimeline(@RequestBody TimelineDTO dto,
	        @RequestHeader Map<String, String> header) {

	    log.info("header : {}", header);
	    Map<String, Object> resp = new HashMap<>();

	    String authorization = header.get("authorization");
	    String loginId = (authorization != null) ? (String) JwtUtils.readToken(authorization).get("id") : null; // 토큰에서 사용자 ID 추출

	    if (loginId == null || loginId.isEmpty()) { 
	        resp.put("loginYN", "fail");
	        resp.put("msg", "로그인이 필요합니다.");
	        return resp;
	    }

	    dto.setTime_user_id(loginId); // 로그인 ID dto에 설정

	    svc.writeTimeline(dto); // 서비스를 호출해 DB에 저장

	    resp.put("loginYN", "success");
	    resp.put("msg", "타임라인 등록이 완료 되었습니다.");

	    return resp;
	}


	// UPDATE TIMELINE
	@PutMapping("/timeline/update")
	public Map<String, Object> updateTimeline(@RequestBody TimelineDTO dto,
	        @RequestHeader Map<String, String> header) {

	    log.info("header : {}", header);
	    Map<String, Object> resp = new HashMap<>();

	    String authorization = header.get("authorization");
	    String loginId = (authorization != null) ? (String) JwtUtils.readToken(authorization).get("id") : null; // 토큰에서 로그인 ID 추출
	    
	    if (loginId == null || loginId.isEmpty()) { // 로그인한 사용자가 작성자가 아닌 경우
	        resp.put("loginYN", "fail");
	        resp.put("msg", "로그인이 필요합니다.");
	        return resp;
	    }

	    if (!loginId.equals(dto.getTime_user_id())) { // 작성자 ID와 로그인 ID 비교하여 수정 권한 없는 경우 실패 메세지 반환
	        resp.put("loginYN", "fail");
	        resp.put("msg", "수정 권한이 없습니다.");
	        return resp;
	    }

	    svc.updateTimeline(dto); 

	    resp.put("loginYN", "success");
	    resp.put("msg", "타임라인이 수정되었습니다.");

	    return resp;
	}

	// DELETE TIMELINE
	@DeleteMapping("/timeline/delete")
	public Map<String, Object> delTimeline(@RequestBody TimelineDTO dto,
	        @RequestHeader Map<String, String> header) {

	    log.info("header : {}", header);
	    Map<String, Object> resp = new HashMap<>();

	    String authorization = header.get("authorization");
	    String loginId = (authorization != null) ? (String) JwtUtils.readToken(authorization).get("id") : null;

	    if (loginId == null || loginId.isEmpty()) {
	        resp.put("loginYN", "fail");
	        resp.put("msg", "로그인이 필요합니다.");
	        return resp;
	    }

	    if (!loginId.equals(dto.getTime_user_id())) { // 작성자 ID와 로그인 ID 비교하여 수정 권한 없는 경우 실패 메세지 반환
	        resp.put("loginYN", "fail");
	        resp.put("msg", "삭제 권한이 없습니다.");
	        return resp;
	    }

	    boolean deleted = svc.delTimeline(dto.getTime_idx(), loginId); // 삭제 실행

	    if (deleted) {
	        resp.put("loginYN", "success");
	        resp.put("msg", "타임라인이 삭제되었습니다.");
	    } else {
	        resp.put("loginYN", "fail");
	        resp.put("msg", "삭제에 실패했습니다.");
	    }

	    return resp;
	}



	// TIMELINE LIST
	@GetMapping("/timeline/list")
	public Map<String, Object> timelineList(@RequestHeader Map<String, String> header) {
	    Map<String, Object> resp = new HashMap<>();

	    String authorization = header.get("authorization");
	    String loginId = (authorization != null) ? (String) JwtUtils.readToken(authorization).get("id") : null;

	    if (loginId == null || loginId.isEmpty()) {
	        resp.put("loginYN", "fail");
	        resp.put("msg", "로그인을 진행해주세요.");
	        return resp;
	    }

	    Map<String, List<TimelineDTO>> t_list_by_year = svc.timelineList(loginId); // 연도별 타임라인 조회
	    resp.put("loginYN", "success");
	    resp.put("data", t_list_by_year); // 연도별로 묶은 데이터 반환

	    return resp;
	}

	// TIMELINE LIST (OTHER USER)
	@GetMapping("/timeline/public/{id}") // {id}는 프로필 주인 id
	public Map<String, Object> publicList(@PathVariable String id, @RequestHeader Map<String, String> header) {
	    Map<String, Object> resp = new HashMap<>();

	    String authorization = header.get("authorization");
	    String loginId = (authorization != null) ? (String) JwtUtils.readToken(authorization).get("id") : null; // 공개 리스트 조회

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
	        map.put("isMine", t.getTime_user_id().equals(loginId)); // 로그인한 사용자가 쓴 글인지 구분
	        return map;
	    }).collect(Collectors.toList());

	    resp.put("loginYN", "success");
	    resp.put("data", result); // 리스트 변환

	    return resp;
	}


}
