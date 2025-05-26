package com.withcare.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.withcare.report.dto.BlockListDTO;
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

    // 쪽지 차단(유저)
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
    @GetMapping("/msg/block/list/{id}")
    public Map<String, Object> blockList(
            @PathVariable String id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestHeader Map<String, String> header
    ) {
        log.info("{} 이 차단한 회원 리스트 목록 (page: {})", id, page);
        Map<String, Object> result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("page", page);
            params.put("pageSize", pageSize);

            Map<String, Object> data = svc.blockList(params);
            result.put("result", data);
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }


    // 차단 해제(유저)
    @DeleteMapping("/msg/block/list/cancel")
    public Map<String, Object> blockCancel(@RequestBody Map<String, Object> params, @RequestHeader Map<String, String> header) {
        log.info("차단 취소 : {}", params);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result.put("result", svc.blockCancel(params));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 차단 관리 페이지(관리자)
    @GetMapping("/admin/block/list")
    public Map<String, Object> getBlockList(
            @RequestParam Map<String, Object> params,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestHeader Map<String, String> header
    ) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            params.put("page", page);
            params.put("pageSize", pageSize);

            Map<String, Object> data = svc.getBlockList(params);
            result.put("result", data);
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }


    // 차단 실행(관리자)
    @PostMapping("/admin/block/process")
    public Map<String, Object> blockProcess(@RequestBody Map<String, Object> params,
                                            @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result.put("result", svc.blockProcess(params));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 차단 해제(관리자)
    @PutMapping("/admin/block/cancel")
    public Map<String, Object> blockAdminCancel(@RequestBody Map<String, Object> params,
                                                @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result.put("result", svc.blockAdminCancel(params));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 차단 상세보기(관리자)
    @PostMapping("/admin/block/detail")
    public Map<String, Object> blockDetail(@RequestBody Map<String, Object> params,
                                           @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result.put("result", svc.blockDetail(params));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }
}
