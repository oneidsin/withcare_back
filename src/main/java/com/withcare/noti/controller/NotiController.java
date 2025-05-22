package com.withcare.noti.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.noti.service.NotiService;
import com.withcare.util.JwtToken;

@CrossOrigin
@RestController
public class NotiController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    NotiService svc;

    Map<String, Object> result = null;

    // 사용자의 알림 목록
    @GetMapping("/noti/list/{id}")
    public Map<String, Object> getNoti(@PathVariable String id, @RequestParam(defaultValue = "0") int offset,
            @RequestHeader Map<String, String> header) {
        log.info("getNoti : {} {}", id, offset);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            result.put("result", svc.getNoti(id, offset));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 알림 삭제 (1개)
    @DeleteMapping("/noti/del/{id}/{noti_idx}")
    public Map<String, Object> deleteNoti(@PathVariable String id, @PathVariable int noti_idx,
            @RequestHeader Map<String, String> header) {
        log.info("deleteNoti : {} {}", id, noti_idx);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean success = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            success = svc.deleteNoti(id, noti_idx);
        }

        result.put("success", success);
        return result;
    }

    // 알림 전체 삭제
    @DeleteMapping("/noti/delAll/{id}")
    public Map<String, Object> deleteAllNoti(@PathVariable String id,
            @RequestHeader Map<String, String> header) {
        log.info("delete all noti : {}", id);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean success = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            success = svc.deleteAllNoti(id);
        }

        result.put("success", success);
        return result;
    }

    // 알림 읽음 확인
    @PutMapping("/noti/read/{id}/{noti_idx}")
    public Map<String, Object> readNoti(@PathVariable String id, @PathVariable int noti_idx,
            @RequestHeader Map<String, String> header) {
        log.info("read Noti : {} {}", id, noti_idx);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean success = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            success = svc.readNoti(id, noti_idx);
        }

        result.put("success", success);
        return result;
    }
}
