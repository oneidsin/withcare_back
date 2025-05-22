package com.withcare.statistic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.board.dto.BoardDTO;

@Mapper
public interface PostStatDAO {

	List<BoardDTO> getBoardPostAndCom();

	int getCommentWeeklyCount();

	int getPostWeeklyCount();

	double getPostAndCom();

}
