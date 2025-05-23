package com.withcare.report.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.report.dto.ReportDTO;
import com.withcare.report.service.ReportService;
import com.withcare.util.JwtToken;

@CrossOrigin
@RestController
public class ReportController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	ReportService svc;

	Map<String, Object> result = null;

	// 신고 관리 - 신고 히스토리 불러오기
	@GetMapping("/admin/report/list")
	public Map<String, Object> reportList(@RequestBody Map<String, Object> params,
			@RequestHeader Map<String, String> header) {
		log.info("header : {}", header);
		result = new HashMap<>();

		String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
		boolean login = false;

		if (!loginId.equals("") && loginId.equals(params.get("id"))) {
			result.put("result", svc.reportList());
			login = true;
		}

		result.put("loginYN", login);
		return result;
	}

	// 신고 카테고리 불러오기
	@GetMapping("/admin/report-manage/report-cate-list")
	public Map<String, Object> reportCateList(@RequestBody Map<String, Object> params,
			@RequestHeader Map<String, String> header) {
		log.info("header : {}", header);
		result = new HashMap<>();

		String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
		boolean login = false;

		if (!loginId.equals("") && loginId.equals(params.get("id"))) {
			result.put("result", svc.reportCateList());
			login = true;
		}

		result.put("loginYN", login);
		return result;
	}

	// 신고 카테고리 추가
	@PostMapping("/admin/report-manage/report-cate-add")
	public Map<String, Object> reportCateAdd(@RequestBody Map<String, Object> params,
			@RequestHeader Map<String, String> header) {
		log.info("header : {}", header);
		result = new HashMap<>();

		String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
		boolean login = false;

		if (!loginId.equals("") && loginId.equals(params.get("id"))) {
			result = svc.reportCateAdd(params);
			login = true;
		}

		result.put("loginYN", login);
		return result;
	}

	// 신고 카테고리 수정
	@PutMapping("/admin/report-manage/report-cate-update")
	public Map<String, Object> reportCateUpdate(@RequestBody Map<String, Object> params,
			@RequestHeader Map<String, String> header) {
		log.info("header : {}", header);
		result = new HashMap<>();

		String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
		boolean login = false;

		if (!loginId.equals("") && loginId.equals(params.get("id"))) {
			result = svc.reportCateUpdate(params);
			login = true;
		}

		result.put("loginYN", login);
		return result;
	}

	// 신고 카테고리 활성화 여부
	@PutMapping("/admin/report-manage/report-cate-active")
	public Map<String, Object> reportCateActive(@RequestBody Map<String, Object> params,
			@RequestHeader Map<String, String> header) {
		log.info("header : {}", header);
		result = new HashMap<>();

		String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
		boolean login = false;

		if (!loginId.equals("") && loginId.equals(params.get("id"))) {
			result = svc.reportCateActive(params);
			login = true;
		}

		result.put("loginYN", login);
		return result;
	}

	/* 유저가 신고하는 기능들 */
	// 신고하기
	@PostMapping("/report")
	public Map<String, Object> report(@RequestBody ReportDTO dto,
			@RequestHeader Map<String, String> header) {
		log.info("신고 접수 : {}", dto);
		log.info("header : {}", header);
		result = new HashMap<>();

		String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
		boolean login = false;

		if (!loginId.equals("") && loginId.equals(dto.getReporter_id())) {
			result = svc.report(dto);
			login = true;
		}

		result.put("loginYN", login);
		return result;
	}
}
