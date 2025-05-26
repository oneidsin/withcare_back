package com.withcare.board.controller;

import java.util.HashMap;
import java.util.List;
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
import com.withcare.util.JwtToken.JwtUtils;

@CrossOrigin
@RestController
public class BoardController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	HashMap<String, Object> result = null;
	
	@Autowired BoardService svc;
	
	// 게시판 생성 (PostMapping)
	@PostMapping("/board/write")  // 레벨 열람 제한 체크 필요 (xml 에서 lv_idx 값 넣어주는 형식으로 가면 될 듯!)
	public Map<String, Object>boardWrite(
			@RequestBody BoardDTO boardDTO,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();
		
	    String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
	    boolean login = false;
		
	    // 로그인한 사용자 아이디로 레벨 정보 조회 (서비스 메서드 필요)
	    int lvIdx = svc.userLevel(loginId);
		
	    if(lvIdx != 7) {
	    	// 관리자 아니면 게시판 생성 불가
	        result.put("success", false);
	        return result;
	    }
	    
	    boolean success = false;
	    if (loginId != null && !loginId.isEmpty()) {
	    	success = svc.boardWrite(boardDTO);
	        login = true;
	    }
	    
		result.put("idx", boardDTO.getBoard_idx());
		result.put("success", success);
	    result.put("loginYN", login);
		
		return result;
	}
	
	// 게시판 수정 (PutMapping)
	@PutMapping("/board/update") // 레벨 열람 제한 체크 필요
	public Map<String, Object>boardUpdate(
			@RequestBody BoardDTO boardDTO,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();
		
		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
	    boolean login = false;
		
	    // 로그인한 사용자 아이디로 레벨 정보 조회 (서비스 메서드 필요)
	    int lvIdx = svc.userLevel(loginId);
		
	    if(lvIdx != 7) {
	    	// 관리자 아니면 게시판 생성 불가
	        result.put("success", false);
	        return result;
	    }
	    
	    boolean success = false;
	    if (loginId != null && !loginId.isEmpty()) {
	    	success = svc.boardUpdate(boardDTO);
	        login = true;
	    }
	    
		result.put("idx", boardDTO.getBoard_idx());
		result.put("success", success);
	    result.put("loginYN", login);
		
		return result;
	}
	
	// 게시판 블라인드
	@PutMapping("/board/delete")
	public Map<String, Object>boardDelete(
			@RequestBody BoardDTO boardDTO,
			@RequestHeader Map<String, String>header){
		
		result = new HashMap<String, Object>();

		String loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
	    boolean login = false;
		
	    // 로그인한 사용자 아이디로 레벨 정보 조회 (서비스 메서드 필요)
	    int lvIdx = svc.userLevel(loginId);
	    
	    if(lvIdx != 7) {
	        result.put("success", false);
	        return result;
	    }
	    
	    boolean success = false;
	    if (loginId != null && !loginId.isEmpty()) {
	    	success = svc.boardDelete(boardDTO);
	        login = true;
	    }
	    
		result.put("idx", boardDTO.getBoard_idx());
		result.put("success", success);
	    result.put("loginYN", login);
		
		return result;
	}
	
	@GetMapping("/board/list")
	public List<BoardDTO> getAllBoards() {
	    return svc.getAllBoards();
	}
	
	@GetMapping("/board/{board_idx}")
	public BoardDTO boardIdx(@PathVariable int board_idx) {
	    return svc.boardIdx(board_idx);
	}

}
