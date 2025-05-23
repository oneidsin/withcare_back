package com.withcare.statistic.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.statistic.dao.ReportStatDAO;
import com.withcare.statistic.dto.ReportTypeStatDTO;
import com.withcare.statistic.dto.ReportReasonStatDTO;
import com.withcare.statistic.dto.ReportStatDTO;

@Service
public class ReportStatService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	ReportStatDAO dao;

	public List<ReportStatDTO> getReport() {
		return dao.getReport();
	}

	public List<ReportTypeStatDTO> getReportType() {
		return dao.getReportType();
	}

	public List<ReportReasonStatDTO> getReportReason() {
		return dao.getReportReason();
	}

}
