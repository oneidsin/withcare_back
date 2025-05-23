package com.withcare.report.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.withcare.report.dao.ReportDAO;

@Service
public class ReportService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	ReportDAO dao;

	public List<Map<String, Object>> reportCateList() {
		return dao.reportCateList();
	}

	public Map<String, Object> reportCateAdd(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<>();

		// 중복 체크
		int duplicateCount = dao.checkDuplicateCate(params);
		if (duplicateCount > 0) {
			log.info("중복된 카테고리명이 있습니다 : {}", params.get("cate_name"));
			result.put("success", false);
			result.put("duplicate", true);
			result.put("msg", "중복된 카테고리명이 있습니다.");
			return result;
		}

		int row = dao.reportCateAdd(params);
		result.put("success", row > 0);
		result.put("duplicate", false);
		result.put("msg", row > 0 ? "카테고리가 추가되었습니다." : "카테고리 추가에 실패했습니다.");
		return result;
	}

	public Map<String, Object> reportCateUpdate(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<>();

		// 중복 체크 (현재 카테고리명은 제외)
		int duplicateCount = dao.checkDuplicateCateForUpdate(params);
		if (duplicateCount > 0) {
			log.info("중복된 카테고리명이 있습니다 : {}", params.get("cate_name"));
			result.put("success", false);
			result.put("duplicate", true);
			result.put("msg", "중복된 카테고리명이 있습니다.");
			return result;
		}

		int row = dao.reportCateUpdate(params);
		result.put("success", row > 0);
		result.put("duplicate", false);
		result.put("msg", row > 0 ? "카테고리가 수정되었습니다." : "카테고리 수정에 실패했습니다.");
		return result;
	}

	public Map<String, Object> reportCateActive(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<>();
		int row = dao.reportCateActive(params);
		result.put("success", row > 0);
		result.put("msg", row > 0 ? "카테고리 상태가 변경되었습니다." : "카테고리 상태 변경에 실패했습니다.");
		return result;
	}

	public Map<String, Object> report(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<>();

		try {
			// 중복 신고 체크
			int duplicateCount = dao.checkDuplicateReport(params);
			if (duplicateCount > 0) {
				result.put("success", false);
				result.put("msg", "이미 신고한 게시글입니다.");
				return result;
			}

			// 신고 처리
			int row = dao.report(params);
			result.put("success", row > 0);
			result.put("msg", row > 0 ? "신고가 접수되었습니다." : "신고접수에 실패했습니다.");

		} catch (Exception e) {
			log.error("신고 처리 중 오류 발생", e);
			result.put("success", false);
			result.put("msg", "신고 처리 중 오류가 발생했습니다.");
		}

		return result;
	}

}
