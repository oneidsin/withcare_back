package com.withcare.board.service;

import java.util.HashMap;
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
    
	public Map<String, Object> boardList(int board_idx, int page, String id) {

		int boardRead = boardLevel(board_idx); // 게시판 열람 레벨 가져오기
		int userLevel = userLevel(id); // 사용자 레벨 가져오기
        
		Map<String, Object> result = new HashMap<String, Object>();
		
        if (userLevel < boardRead && userLevel != 7) { // 게시판 열람 레벨 보다 낮고, level_idx 가 7이 아니면
            result.put("page", page);
            result.put("totalPages", 0);
            result.put("totalPosts", 0);
            return result;
        }

        // PostService에서 실제 게시글 리스트 받아오기
        return postService.postList(board_idx, page);
    }
	
}
