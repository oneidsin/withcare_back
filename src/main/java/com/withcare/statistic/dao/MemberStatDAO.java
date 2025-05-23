package com.withcare.statistic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.statistic.dto.MemberStatCntDTO;
import com.withcare.statistic.dto.MemberStatDTO;

@Mapper
public interface MemberStatDAO {

	// 연령대별 + 성별별 회원 리스트 조회
	List<MemberStatDTO> getMember();

	// 전체 회원 수 / 일간 신규 가입자 수 조회
	List<MemberStatCntDTO> getMemberCnt();

}
