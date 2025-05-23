package com.withcare.statistic.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.statistic.dto.BlockCountDTO;
import com.withcare.statistic.service.BlockStatService;

@RestController
@CrossOrigin
public class BlockStatController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	BlockStatService svc;

	// 종합 / 주간 차단 건수 7일간
	@GetMapping("/stat/block")
    public BlockCountDTO getBlock() {
        return svc.getBlock();
  }
	
	// 차단 이유별 분포
	@GetMapping("/stat/block-reason")
	public Map<String, Object> getBlockReason() {
		return svc.getBlockReason();
	}

    // 유저간 차단 통계
	
	
	
	
}
