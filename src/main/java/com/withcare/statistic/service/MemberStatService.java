package com.withcare.statistic.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.statistic.dao.MemberStatDAO;
import com.withcare.statistic.dto.MemberStatCntDTO;
import com.withcare.statistic.dto.MemberStatDTO;

@Service
public class MemberStatService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired MemberStatDAO dao;

	// 연령대별 + 성별별 회원 리스트 조회
	public List<MemberStatDTO> getMember() {
		return dao.getMember();
	}

	// 전체 회원 수 / 일간 신규 가입자 수 조회
	public List<MemberStatCntDTO> getMemberCnt() {
		return dao.getMemberCnt();
	}
}
