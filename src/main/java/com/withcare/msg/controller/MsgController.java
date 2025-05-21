package com.withcare.msg.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.msg.dto.MsgDTO;
import com.withcare.msg.service.MsgService;

@CrossOrigin
@RestController
public class MsgController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired MsgService svc;
	
	// Send MSG
	@PostMapping ("/msg/send")
	public String sendMsg (@RequestBody MsgDTO dto) {
		svc.sendMsg(dto);
		
		return "📫 쪽지가 성공적으로 전송되었습니다.";
	}
	
	// OUTBOX
	@GetMapping("/msg/outbox")
	public List<MsgDTO> outbox(@RequestParam("sender_id") String id){
		return svc.outbox(id);
	}
	
	// INBOX
		@GetMapping("/msg/inbox")
		public List<MsgDTO> inbox(@RequestParam("receiver_id") String id){
			return svc.inbox(id);
		}
	

	
}
