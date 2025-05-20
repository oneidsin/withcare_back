package com.withcare.profile.controller;

import com.withcare.profile.dto.MemberActivityDTO;
import com.withcare.profile.dto.MemberLevelDTO;
import com.withcare.profile.service.LevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
public class LevelController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    LevelService svc;

    // 사용자 레벨 정보 가져오기
    @GetMapping("/{id}/level/list")
    public MemberLevelDTO getMemberLevel(@PathVariable String id) {
        log.info("레벨 요청 아이디 : {}", id);
        return svc.getMemberLevel(id);
    }

    // 사용자의 작성글 수, 댓글 수, 추천받은 수, 타임라인 수, 접속 수
    @GetMapping("/{id}/level/activity")
    public MemberActivityDTO getMemberActivity(@PathVariable String id) {
        MemberActivityDTO dto = svc.getMemberActivity(id);
        log.info("activity : {}", dto);
        return dto;
    }

    // 레벨 조건 계산 후 레벨 업데이트
    @GetMapping("/{id}/level/update")
    public Map<String, Object> calculateAndUpdateLevel(@PathVariable String id) {
        log.info("레벨 자동 계산 요청: {}", id);
        return svc.calculateAndUpdateLevel(id);
    }
}
