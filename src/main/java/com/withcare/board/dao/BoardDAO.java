package com.withcare.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.board.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	int boardWrite(BoardDTO boardDTO);

	int boardUpdate(BoardDTO boardDTO);

	int boardDelete(BoardDTO boardDTO);

	Integer boardLevel(int board_idx);

	int userLevel(String id);

	boolean boardComYn(int board_idx);

	List<BoardDTO> selectAllBoards();

	BoardDTO boardIdx(int board_idx);

}
