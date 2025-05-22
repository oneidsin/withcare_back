package com.withcare.statistic.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.statistic.dao.BlockStatDAO;

@Service
public class BlockStatService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	BlockStatDAO dao;

	public Map<String, Object> getBlockStat() {
		Map<String, Object> result = new HashMap<>();
		result.put("totalBlockCount", dao.getTotalCount());
		result.put("weeklyBlockCount", dao.getWeeklyCount());
		result.put("blockReason", dao.getBlockReason()); // 이게 List<Map<String, Object>>
		return result;
	}
}
