package com.withcare.statistic.controller;

import java.util.HashMap;
import java.util.List;
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
    public Map<String, Integer> getBlockReason() {
        List<Map<String, Object>> rawData = svc.getBlockReason();

        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Object> row : rawData) {
            String reason = (String) row.get("block_reason");
            Integer count = ((Number) row.get("reason_count")).intValue();
            result.put(reason, count);
        }
        return result;
    }

    // 유저간 차단 통계 종힙 / 주간 차단 건수 7일간
	@GetMapping("/stat/block-usertouser")
    public BlockCountDTO getBlockUTU() {
        return svc.getBlockUTU();
	}
}
