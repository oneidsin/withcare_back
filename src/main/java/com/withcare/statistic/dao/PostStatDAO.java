package com.withcare.statistic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.statistic.dto.PostStatDTO;

@Mapper
public interface PostStatDAO {

	int getCommentWeeklyCount();

	int getPostWeeklyCount();

	List<PostStatDTO> getPostAndCom();

}
