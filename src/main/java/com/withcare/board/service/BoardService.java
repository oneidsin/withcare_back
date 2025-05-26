package com.withcare.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.board.dao.BoardDAO;
import com.withcare.board.dto.BoardDTO;
import com.withcare.post.service.PostService;

@Service
public class BoardService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	HashMap<String, Object> result = null;
	
	@Autowired BoardDAO dao;
	@Autowired PostService postService;

	public boolean boardWrite(BoardDTO boardDTO) {
		int row = dao.boardWrite(boardDTO);
		return row>0;
	}

	public boolean boardUpdate(BoardDTO boardDTO) {
		int row = dao.boardUpdate(boardDTO);
		return row>0;
	}

	public boolean boardDelete(BoardDTO boardDTO) {
		int row = dao.boardDelete(boardDTO);
		return row>0;
	}

	public int boardLevel(int board_idx) {
		return dao.boardLevel(board_idx);
	}

    public int userLevel(String id) {
        return dao.userLevel(id);
    }

	public List<BoardDTO> getAllBoards() {
		return dao.selectAllBoards();
	}

	public BoardDTO boardIdx(int board_idx) {
		return dao.boardIdx(board_idx);
	}
	
}
