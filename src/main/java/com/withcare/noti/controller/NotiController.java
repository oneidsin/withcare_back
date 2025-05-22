package com.withcare.noti.controller;

import com.withcare.noti.dto.NotiDTO;
import com.withcare.noti.service.NotiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    @GetMapping("/noti/list/{id}")
    public Map<String, Object> getNoti(@PathVariable String id) {
        log.info("getNoti : {}", id);
        result = new HashMap<>();
        result.put("result", svc.getNoti(id));
        return result;
    }

    // 알림 삭제 (1개)
    @DeleteMapping("/noti/del/{id}/{noti_idx}")
    public Map<String, Object> deleteNoti(@PathVariable String id, @PathVariable int noti_idx) {
        log.info("deleteNoti : {} {}", id, noti_idx);
        result = new HashMap<>();
        boolean success = svc.deleteNoti(id, noti_idx);
        result.put("success", success);
        return result;
    }


}
