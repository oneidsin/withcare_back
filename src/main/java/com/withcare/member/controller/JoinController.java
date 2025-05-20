package com.withcare.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.member.service.JoinService;
import com.withcare.util.JwtToken.JwtUtils;

@CrossOrigin
@RestController
public class JoinController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	Map<String, Object> result = null;
	
	@Autowired JoinService svc;
	
	@RequestMapping("/")
	public Map<String, Object> home () {
		result = new HashMap<String, Object>();
		result.put("msg", "Hello, To-Do List");
		return result;
	}
	
	// 중복 체크
	
	@GetMapping("/overlay/{id}")
	public Map<String, Object> overlay(@PathVariable String id){
		log.info(id+"중복 체크");
		result = new HashMap<String, Object>();
		boolean success = svc.overlay(id);
		result.put("use", success);
		
		return result;
	}
	
	// 회원가입
	@PostMapping("/join")
	public Map<String, Object> join(@RequestBody Map<String, String> params){
		
		log.info("회원가입 : "+params);
		
		boolean success = svc.join(params);
		result = new HashMap<String,Object>();
		result.put("success", success);
		
		return result;
	}
   
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
