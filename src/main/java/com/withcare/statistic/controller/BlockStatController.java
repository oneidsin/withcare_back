package com.withcare.statistic.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.statistic.service.BlockStatService;

@RestController
@CrossOrigin
public class BlockStatController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	BlockStatService svc;

	@GetMapping("/block")
    public Map<String, Object> getBlockStat() {
        return svc.getBlockStat();
    }
}
