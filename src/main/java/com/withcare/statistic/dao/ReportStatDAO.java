package com.withcare.statistic.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportStatDAO {

	List<Map<String, Object>> getTotalAndWeeklyCount();

	List<Map<String, Object>> getReportReason();

	List<Map<String, Object>> getReportStatus();

	List<Map<String, Object>> getReportProcessReason();

}
