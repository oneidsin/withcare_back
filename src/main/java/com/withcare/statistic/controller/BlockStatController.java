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

	// 차단 통계
	@GetMapping("/stat/block_reason")
	public Map<String, Object> getBlockStatistics() {
		return svc.getBlockStat();
	}

    // 유저간 차단 통계
	
	
	
	
}
