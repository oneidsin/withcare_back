package com.withcare.noti.controller;

import com.withcare.noti.dto.NotiDTO;
import com.withcare.noti.service.NotiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class NotiController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    NotiService svc;

    Map<String, Object> result = null;

    // 사용자의 알림 목록
    @GetMapping("/noti")
    public List<NotiDTO> getNoti(@RequestBody Map<String, String> params) {
        log.info("getNoti : {}", params.get("id"));
        return svc.getNoti(params);
    }

    @DeleteMapping("/noti/delete")
    public Map<String, Object> deleteNoti(@RequestBody Map<String, String> params) {
        log.info("deleteNoti : {}", params.get("id"));

        return result;
    }


}
