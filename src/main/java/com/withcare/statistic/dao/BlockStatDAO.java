package com.withcare.statistic.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.statistic.dto.BlockCountDTO;

@Mapper
public interface BlockStatDAO {

	// 종합 / 주간 차단 건수 7일간
	BlockCountDTO getBlock();
	
	
	// 차단 이유별 분포
	List<Map<String, Object>> getBlockReason();

}
