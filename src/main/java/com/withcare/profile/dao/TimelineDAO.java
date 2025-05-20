package com.withcare.profile.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.withcare.profile.dto.TimelineDTO;

@Mapper
public interface TimelineDAO {

	// 타임라인 작성
    void writeTimeline(TimelineDTO dto);

    // 타임라인 수정
    void updateTimeline(TimelineDTO dto);

    // 타임라인 삭제
    void delTimeline(@Param("time_idx") int time_idx);

    // 삭제 권한 체크용: time_idx로 작성자 아이디 조회
    String get_token(@Param("time_idx") int time_idx);

    // 유저의 타임라인 리스트
	List<TimelineDTO> timelineList(String id);

	// 타 유저의 공개 타임라인
	List<TimelineDTO> publicList(String id);
}
