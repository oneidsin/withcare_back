package com.withcare.msg.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		
	// MSG DETAIL
	@GetMapping("/msg/detail")
	public MsgDTO msgDetail (@RequestParam("msg_idx") int idx) {
		return svc.msgDetail(idx);
	}
		
	// DEL MSG (INBOX 기준)
	@PutMapping("msg/delete/inbox")
	public String msgDel (@RequestParam("msg_idx") int idx) {
		svc.msgDel(idx);
		return "✉️ 받은 쪽지가 삭제 처리 되었습니다.";
	}
		
	// DEL MSG (OUTBOX 기준)
	@PutMapping("msg/delete/outbox")
	public String msgDelOut (@RequestParam("msg_idx") int idx) {
		svc.msgDelOut(idx);
		return "✉️ 보낸 쪽지가 삭제 처리 되었습니다.";
	}
	
	// SAVE MSG (INBOX 기준)
	@PutMapping("msg/save/inbox")
	public String msgSave (@RequestParam("msg_idx") int idx) {
		svc.msgSave(idx);
		return "📬 받은 쪽지가 보관 처리 되었습니다.";
	}
			
	// SAVE MSG (OUTBOX 기준)
	@PutMapping("msg/save/outbox")
	public String msgSaveOut (@RequestParam("msg_idx") int idx) {
		svc.msgSaveOut(idx);
		return "📬️ 보낸 쪽지가 보관 처리 되었습니다.";
	}
	
		
	

	
}
