package com.withcare.noti.controller;

import com.withcare.noti.dto.NotiDTO;
import com.withcare.noti.service.NotiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class NotiController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    NotiService svc;

    // 사용자의 알림 목록
    @GetMapping("/{id}/noti")
    public List<NotiDTO> getNoti(@PathVariable String id) {
        return svc.getNoti(id);
    }
}
