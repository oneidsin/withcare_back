package com.withcare.msg.service;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.msg.dao.MsgDAO;
import com.withcare.msg.dto.MsgDTO;

@Service
public class MsgService {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired MsgDAO dao;

	// Send MSG
	public void sendMsg(MsgDTO dto) {
		dto.setMsg_sent_at(new Date(System.currentTimeMillis()));
		dto.setMsg_read(false);
		dto.setSender_msg_status("N");// N : 초기 상태 S : 보관 D : 삭제
		dto.setReceiver_msg_status("N");
		
		dao.sendMsg(dto);
	}

}
