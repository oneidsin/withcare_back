package com.withcare.profile.controller;

import com.withcare.profile.service.BadgeService;
import com.withcare.util.JwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class BadgeController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    BadgeService svc;

    Map<String, Object> result = null;

    // 배지 리스트 가져오기(획득한 것, 획득하지 않은 것)
    @GetMapping("/{id}/badge/list")
    public Map<String, Object> getMemberBadgeList(@PathVariable String id,
                                                  @RequestHeader Map<String, String> header) {
        result = new HashMap<>();
        String loginId = getLoginIdFromToken(header);

        boolean loginYN = loginId != null && loginId.equals(id);
        result.put("loginYN", loginYN);

        if (!loginYN) {
            result.put("error", "Invalid token");
            return result;
        }

        log.info("배지 리스트 요청 : {}", id);
        result.put("result", svc.getMemberBadgeList(id));
        return result;
    }

    // 유저가 획득한 배지만 조회
    @GetMapping("/{id}/badge/acquired")
    public Map<String, Object> getMemberAcquiredBadge(@PathVariable String id,
                                                      @RequestHeader Map<String, String> header) {
        result = new HashMap<>();
        String loginId = getLoginIdFromToken(header);

        boolean loginYN = loginId != null && loginId.equals(id);
        result.put("loginYN", loginYN);

        if (!loginYN) {
            result.put("error", "Invalid token");
            return result;
        }

        log.info("유저가 획득한 배지만 조회 요청 : {}", id);
        result.put("result", svc.getMemberAcquiredBadge(id));
        return result;
    }

    // 배지 획득하기
    @GetMapping("/{id}/badge/acquired/{bdg_idx}")
    public Map<String, Object> acquiredBadge(@PathVariable String id,
                                             @PathVariable int bdg_idx,
                                             @RequestHeader Map<String, String> header) {
        result = new HashMap<>();
        String loginId = getLoginIdFromToken(header);

        boolean loginYN = loginId != null && loginId.equals(id);
        result.put("loginYN", loginYN);

        if (!loginYN) {
            result.put("error", "Invalid token");
            return result;
        }

        log.info("배지 획득 요청 : {}, {}", id, bdg_idx);
        result.put("result", svc.acquiredBadge(id, bdg_idx));
        return result;
    }

    // 대표 배지 설정
    @PutMapping("/{id}/badge/sym_yn/{bdg_idx}")
    public Map<String, Object> updateBadgeSym(@PathVariable String id,
                                              @PathVariable int bdg_idx,
                                              @RequestHeader Map<String, String> header) {
        result = new HashMap<>();
        String loginId = getLoginIdFromToken(header);

        boolean loginYN = loginId != null && loginId.equals(id);
        result.put("loginYN", loginYN);

        if (!loginYN) {
            result.put("error", "Invalid token");
            return result;
        }

        log.info("대표 배지 설정 요청 : {}, {}", id, bdg_idx);
        result.put("result", svc.updateBadgeSym(id, bdg_idx));
        return result;
    }

    // 토큰에서 로그인 ID 추출
    private String getLoginIdFromToken(Map<String, String> header) {
        try {
            String token = header.get("authorization");
            if (token == null || token.isEmpty()) return null;

            Map<String, Object> tokenData = JwtToken.JwtUtils.readToken(token);
            return (String) tokenData.get("id");
        } catch (Exception e) {
            log.warn("Token validation failed", e);
            return null;
        }
    }
}
