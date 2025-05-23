package com.withcare.profile.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.profile.service.LevelService;
import com.withcare.util.JwtToken;

@CrossOrigin
@RestController
public class LevelController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    LevelService svc;

    Map<String, Object> result = null;

    // 사용자 레벨 정보 가져오기
    @GetMapping("/{id}/level/list")
    public Map<String, Object> getMemberLevel(@PathVariable String id, @RequestHeader Map<String, String> header) {
        log.info("레벨 리스트 요청 아이디 : {}", id);
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            result.put("result", svc.getMemberLevel(id));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 사용자의 작성글 수, 댓글 수, 추천받은 수, 타임라인 수, 접속 수
    @GetMapping("/{id}/level/activity")
    public Map<String, Object> getMemberActivity(@PathVariable String id, @RequestHeader Map<String, String> header) {
        log.info("activity 요청 아이디 : {}", id);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            result.put("result", svc.getMemberActivity(id));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 레벨 조건 계산 후 레벨 업데이트
    @GetMapping("/{id}/level/update")
    public Map<String, Object> calculateAndUpdateLevel(@PathVariable String id,
            @RequestHeader Map<String, String> header) {
        log.info("레벨 자동 계산 요청: {}", id);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            result.put("result", svc.calculateAndUpdateLevel(id));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

}
