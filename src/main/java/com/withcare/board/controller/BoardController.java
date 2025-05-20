package com.withcare.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.board.dto.BoardDTO;
import com.withcare.board.service.BoardService;

@CrossOrigin
@RestController
public class BoardController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	HashMap<String, Object> result = null;
	
	@Autowired BoardService svc;
	
	// 게시판 생성 (PostMapping)
	@PostMapping("/board/write")
	public Map<String, Object>boardWrite(
			@RequestBody BoardDTO boardDTO,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();
		int lvIdx = Integer.parseInt(header.get("lv_idx")); // 레벨 확인
		
	    if(lvIdx != 7) {
	    	// 관리자 아니면 게시판 생성 불가
	        result.put("success", false);
	        return result;
	    }
	    
		boolean success = svc.boardWrite(boardDTO);
		result.put("idx", boardDTO.getBoard_idx());
		result.put("success", success);
		
		return result;
	}
	
	// 게시판 수정 (PutMapping)
	@PutMapping("/board/update")
	public Map<String, Object>boardUpdate(
			@RequestBody BoardDTO boardDTO,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();
		int lvIdx = Integer.parseInt(header.get("lv_idx"));
		
	    if(lvIdx != 7) {
	        result.put("success", false);
	        return result;
	    }
		
		boolean success = svc.boardUpdate(boardDTO);
		result.put("idx", boardDTO.getBoard_idx());
		result.put("success", success);
		
		return result;
	}
	
	// 게시판 블라인드
	@PutMapping("/board/delete")
	public Map<String, Object>boardDelete(
			@RequestBody BoardDTO boardDTO,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();
	    int lvIdx = Integer.parseInt(header.get("lv_idx"));
	    
	    if(lvIdx != 7) {
	        result.put("success", false);
	        return result;
	    }
	    
		boolean success = svc.boardDelete(boardDTO);
		result.put("idx", boardDTO.getBoard_idx());
		result.put("success", success);
		
		return result;
	}
	
	@GetMapping("/board/list/{page}")
	public Map<String, Object>boardList(
			@PathVariable int page,
			@RequestParam int board_idx,
			@RequestHeader Map<String, String>header){
		
		String id = header.get("id");
		return svc.boardList(board_idx, page, id);
	}
	
}
