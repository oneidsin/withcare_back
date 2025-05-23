package com.withcare.report.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public boolean reportCateAdd(Map<String, Object> params) {
		// 중복 체크
		int duplicateCount = dao.checkDuplicateCate(params);
		if (duplicateCount > 0) {
			log.info("중복된 카테고리명이 있습니다 : {}", params.get("cate_name"));
			return false;
		}

		int row = dao.reportCateAdd(params);
		return row > 0;
	}

	public boolean reportCateUpdate(Map<String, Object> params) {
		// 중복 체크 (현재 카테고리 제외)
		int duplicateCount = dao.checkDuplicateCateForUpdate(params);
		if (duplicateCount > 0) {
			log.info("중복된 카테고리명이 있습니다 : {}", params.get("cate_name"));
			return false;
		}

		int row = dao.reportCateUpdate(params);
		return row > 0;
	}

	public boolean reportCateActive(Map<String, Object> params) {
		int row = dao.reportCateActive(params);
		return row > 0;
	}

}
