package com.withcare.statistic.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.statistic.dto.MemberStatCntDTO;
import com.withcare.statistic.dto.MemberStatDTO;
import com.withcare.statistic.service.MemberStatService;

@CrossOrigin
@RestController
public class MemberStatController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberStatService svc;

	// 연령대별 + 성별별 회원 리스트 조회
	@GetMapping("/stat/member-group")
	public List<MemberStatDTO> getMember() {
		return svc.getMember();
	}
	
	// 전체 회원 수 / 일간 신규 가입자 수 조회
	@GetMapping("/stat/member-count")
	public List<MemberStatCntDTO> getMemberCnt() {
		return svc.getMemberCnt();
	}
}
