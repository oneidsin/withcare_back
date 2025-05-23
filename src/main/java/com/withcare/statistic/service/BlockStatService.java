package com.withcare.statistic.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.statistic.dao.BlockStatDAO;
import com.withcare.statistic.dto.BlockCountDTO;

@Service
public class BlockStatService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	BlockStatDAO dao;

	// 종합 / 주간 차단 건수 7일간
	public BlockCountDTO getBlock() {
		return dao.getBlock();
	}
	
	// 차단 이유별 분포
	public Map<String, Object> getBlockReason() {
		Map<String, Object> result = new HashMap<>();
		result.put("reason", dao.getBlockReason()); // 이게 List<Map<String, Object>>
		return result;
	}

	// 유저간 차단 통계 종힙 / 주간 차단 건수 7일간
	public BlockCountDTO getBlockUTU() {
		return dao.getBlockUTU();
	}




	
}
