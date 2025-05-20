package com.withcare.board.dao;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.board.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	int boardWrite(BoardDTO boardDTO);

	int boardUpdate(BoardDTO boardDTO);

	int boardDelete(BoardDTO boardDTO);

	int boardLevel(int board_idx);

	int userLevel(String id);

	boolean boardComYn(int board_idx);

}
