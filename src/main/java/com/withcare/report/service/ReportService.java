package com.withcare.report.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.withcare.report.dao.ReportDAO;
import com.withcare.report.dto.ReportDTO;

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

	public Map<String, Object> report(ReportDTO reportDTO) {
		Map<String, Object> result = new HashMap<>();

		String itemType = reportDTO.getRep_item_type();
		int itemIdx = reportDTO.getRep_item_idx();
		String reporterId = reportDTO.getReporter_id();

		try {
			// 1. 신고 대상 작성자 조회
			String reportedId = null;
			switch (itemType) {
				case "post":
					reportedId = dao.postWriter(itemIdx);
					break;
				case "comment":
					reportedId = dao.commentWriter(itemIdx);
					break;
				case "mention":
					reportedId = dao.mentionWriter(itemIdx);
					break;
				default:
					result.put("success", false);
					result.put("msg", "잘못된 신고 타입입니다.");
					return result;
			}

			if (reportedId == null) {
				result.put("success", false);
				result.put("msg", "신고 대상이 존재하지 않습니다.");
				return result;
			}

			// 2. 자기 자신 신고 방지
			if (reporterId.equals(reportedId)) {
				result.put("success", false);
				result.put("msg", "자신의 콘텐츠는 신고할 수 없습니다.");
				return result;
			}

			// 3. 중복 신고 체크
			if (dao.isDuplicateReport(reporterId, itemType, itemIdx)) {
				result.put("success", false);
				result.put("msg", "이미 신고한 항목입니다.");
				return result;
			}

			// 4. 신고 등록
			reportDTO.setReported_id(reportedId); // 신고 대상
			int row = dao.report(reportDTO);

			// 5. 신고 히스토리 등록
			Map<String, Object> history = new HashMap<>();
			history.put("rep_idx", reportDTO.getRep_idx());
			dao.insertReportHistory(history);

			result.put("success", row > 0);
			result.put("msg", row > 0 ? "신고가 접수되었습니다." : "신고 접수에 실패했습니다.");

		} catch (Exception e) {
			log.error("신고 처리 중 오류", e);
			result.put("success", false);
			result.put("msg", "신고 처리 중 오류가 발생했습니다.");
		}

		return result;
	}

	public List<Map<String, Object>> reportList() {
		return dao.reportList();
	}

}
