package com.withcare.profile.dao;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.profile.dto.TimelineDTO;

@Mapper
public interface TimelineDAO {

	void writeTimeline(TimelineDTO dto);

	int update_timeline(TimelineDTO dto);


}
