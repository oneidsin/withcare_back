package com.withcare.statistic.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.statistic.dao.ReportStatDAO;

@Service
public class ReportStatService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	ReportStatDAO dao;

	/*
	 * public boolean submitReport(ReportDTO dto) { // 해당 서비스에서는 사용되지 않는 기능으로 보입니다
	 * (report용) return false; }
	 */

	public List<Map<String, Object>> getTotalAndWeeklyCount() {
		return dao.getTotalAndWeeklyCount();
	}

	public List<Map<String, Object>> getReportReason() {
		return dao.getReportReason();
	}

	public List<Map<String, Object>> getReportStatus() {
		return dao.getReportStatus();
	}

	public List<Map<String, Object>> getReportProcessReason() {
		return dao.getReportProcessReason();
	}

}
