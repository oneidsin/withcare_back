package com.withcare.statistic.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlockStatDAO {

	int getTotalCount();

	int getWeeklyCount();

	List<Map<String, Object>> getBlockReason();

}
