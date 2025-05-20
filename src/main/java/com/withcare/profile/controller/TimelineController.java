package com.withcare.profile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @Autowired TimelineService svc;
    
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
    public Map<String, Object> writeTimeline(
            @RequestBody TimelineDTO dto,
            @RequestHeader("Authorization") String authorizationHeader) {
        
        String userId = get_token(authorizationHeader);
        log.info("사용자 아이디: {}", userId);
        
        dto.setTime_user_id(userId);
        
        svc.writeTimeline(dto);
        
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("msg", "타임라인 등록이 완료 되었습니다.");
        
        return result;
    }
    
    // 타임라인 수정
    @PutMapping("/timeline/update")
    public ResponseEntity<String> updateTimeline(
            @RequestBody TimelineDTO dto,
            @RequestHeader("Authorization") String authorizationHeader) {
        
        String userId = get_token(authorizationHeader);
        log.info("사용자 아이디: {}", userId);
        
        dto.setTime_user_id(userId);
        svc.update_timeline(dto);
        
        return ResponseEntity.ok("updated");
    }
    
    // 타임라인 삭제
    @DeleteMapping("/timeline/delete")
    public ResponseEntity<String> del_timeline(
            @RequestBody TimelineDTO dto,
            @RequestHeader("Authorization") String authorizationHeader) {
        
        String userId = get_token(authorizationHeader);
        log.info("사용자 아이디: {}", userId);

        boolean deleted = svc.del_timeline(dto.getTime_idx(), userId);
        if (deleted) {
            return ResponseEntity.ok("deleted");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
        }
    }
    
    // 유저의 타임라인 리스트
    @GetMapping ("/timeline/list")
    public ResponseEntity<?> timeline_list(@RequestHeader("Authorization") String token){
    	Map<String, Object> claims = JwtToken.JwtUtils.readToken(token);
    	String id = (String) claims.get("id");
    	
    	if (id == null || id.isEmpty()) {
    		return ResponseEntity.status(401).body("유효하지 않은 토큰입니다.");
    	}
    	
    Map<String, List<TimelineDTO>> t_list_by_year = svc.timeline_list(id);
    		
    return ResponseEntity.ok(t_list_by_year);
    
    }
    
    // 타 유저의 공개 타임라인 리스트
   @GetMapping ("/timeline/pubic")
   public ResponseEntity<?> public_list() {
	   List<TimelineDTO> public_timeline = svc.public_list();
	   return ResponseEntity.ok(public_timeline);
   }
   
    

}
