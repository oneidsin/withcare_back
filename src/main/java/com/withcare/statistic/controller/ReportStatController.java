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

import com.withcare.statistic.dto.ReportTypeStatDTO;
import com.withcare.statistic.dto.ReportReasonStatDTO;
import com.withcare.statistic.dto.ReportStatDTO;
import com.withcare.statistic.service.ReportStatService;

@RestController
@CrossOrigin
public class ReportStatController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired ReportStatService svc;
	
	// int 로 가져올 수 없기에 list 로 작업했습니다!
	
    // 종합 / 주간 신고 건수 7일간
	@GetMapping("/stat/report")
	public List<ReportStatDTO> getReport(){
		return svc.getReport();
	}

    // 신고 타입별 분포 (글 / 댓글 / 회원)
	@GetMapping("/stat/report-type")
    public Map<String, Integer> getReportType() {
        List<ReportTypeStatDTO> list = svc.getReportType();

        Map<String, Integer> result = new HashMap<>();
        for (ReportTypeStatDTO dto : list) {
            result.put(dto.getRep_item_type(), dto.getWeekly_count());
        }
        return result;
    }
	
	// 신고 사유별 분포 (욕설 / 도배 / 혐오 등)
	@GetMapping("/stat/report-reason")
    public Map<String, Integer> getReportReason() {
        List<ReportReasonStatDTO> list = svc.getReportReason();

        Map<String, Integer> result = new HashMap<>();
        for (ReportReasonStatDTO dto : list) {
            result.put(dto.getCate_name(), dto.getWeekly_count());
        }
        return result;
    }
}
