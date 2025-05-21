package com.withcare.statistic.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.statistic.service.ReportStatService;

@RestController
@CrossOrigin
public class ReportStatController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired ReportStatService svc;
	
	/*
	 * // 신고 등록
	 * 
	 * @PostMapping("/submit") public Map<String, Object> submitReport(@RequestBody
	 * ReportDTO dto) { Map<String, Object> result = new HashMap<>(); try { boolean
	 * isInserted = svc.submitReport(dto); if (isInserted) { result.put("success",
	 * true); result.put("message", "신고가 등록되었습니다."); } else { result.put("success",
	 * false); result.put("message", "이미 신고된 항목입니다."); } } catch (Exception e) {
	 * result.put("success", false); result.put("message", "신고 처리 중 오류 발생"); }
	 * return result; }
	 */

    // 종합, 주간 신고 건수 + 사유
    @GetMapping("/summary")
    public Map<String, Object> getReportSummary() {
        Map<String, Object> result = new HashMap<>();
        result.put("totalAndWeekly", svc.getTotalAndWeeklyCount());
        result.put("reason", svc.getReportReason());
        return result;
    }

    // 신고 처리 통계 (전체, 일주일)
    @GetMapping("/summary_history")
    public Map<String, Object> getHistorySummary() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", svc.getReportStatus());
        result.put("processReason", svc.getReportProcessReason());
        return result;
    }
    
    // 차단 통계
    
    
    // 유저간 차단 통계
    
    
    
	
}
