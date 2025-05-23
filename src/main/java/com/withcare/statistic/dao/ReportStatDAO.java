package com.withcare.statistic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.statistic.dto.ReportTypeStatDTO;
import com.withcare.statistic.dto.ReportReasonStatDTO;
import com.withcare.statistic.dto.ReportStatDTO;

@Mapper
public interface ReportStatDAO {
	
	List<ReportStatDTO> getReport();

	List<ReportTypeStatDTO> getReportType();

	List<ReportReasonStatDTO> getReportReason();

}
