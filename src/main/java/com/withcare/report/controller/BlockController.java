package com.withcare.report.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.withcare.report.service.BlockService;
import com.withcare.util.JwtToken;

@CrossOrigin
@RestController
public class BlockController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    BlockService svc;

    Map<String, Object> result = null;

    // 쪽지 차단
    @PostMapping("/msg/block")
    public Map<String, Object> block(@RequestBody Map<String, Object> params, @RequestHeader Map<String, String> header) {
        log.info("block params: {}", params);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result.put("result", svc.block(params));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 차단한 회원 리스트(유저)
    @GetMapping("/msg/block/{id}")
    public Map<String, Object> blockList(@PathVariable String id, @RequestHeader Map<String, String> header) {
        log.info("{} 이 차단한 회원 리스트 목록 : ", id);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            result = svc.blockList(id);
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }
}
