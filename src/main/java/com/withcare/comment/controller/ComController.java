package com.withcare.comment.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.comment.dto.ComDTO;
import com.withcare.comment.service.ComService;
import com.withcare.post.dto.PostDTO;
import com.withcare.util.JwtToken.JwtUtils;

@CrossOrigin
@RestController
public class ComController {
	
Logger log = LoggerFactory.getLogger(getClass());
	
	Map<String, Object> result = null;
	
	@Autowired ComService svc;
	
	// WRITE COMMENT 
	@PostMapping("/post/detail/{post_idx}/write")
	public Map<String, Object> writeCom (@RequestBody ComDTO dto, @RequestHeader Map<String, String> header) {
		
		result = new HashMap<>();
		
		String id = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		boolean login = false;
		
		dto.setId(id);
		
		boolean success = false;
		if (id != null && !id.isEmpty()) {
			success = svc.writeCom(dto);
			login = true;
		}
		
		result.put("idx", dto.getCom_idx());
		result.put("success", success);
		result.put("loginYN", login);
		
		return result;
	}
	
	
	// 댓글 수정
	@PutMapping("/post/detail/{post_idx}/edit")
	public Map<String, Object> editCom (@RequestBody ComDTO dto, @RequestHeader Map<String, String> header){
		
		result = new HashMap<>();
		
		String id = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		boolean login = false;
		boolean success = false;
		
		dto.setId(id);
		
		if (id != null && !id.isEmpty()) {
			login = true;
			success = svc.updateCom(dto);
		}
		
		result.put("success", success);
		result.put("loginYN", login);
		
		return result;
	}
	
	// 댓글 블라인드
	// @PutMapping
	
	// 댓글 리스트 (조회)
	// @GetMapping
	
	
	
	
	
	
	
	
	
	
	
}
