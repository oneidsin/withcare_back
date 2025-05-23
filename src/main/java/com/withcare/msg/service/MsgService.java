package com.withcare.msg.service;

import java.sql.Timestamp;
import java.util.List;

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
		dto.setMsg_sent_at(new Timestamp(System.currentTimeMillis()));
		dto.setMsg_read(false);
		dto.setSender_msg_status("N");// N : 초기 상태 S : 보관 D : 삭제
		dto.setReceiver_msg_status("N");

		dao.sendMsg(dto);
	}

	// OUTBOX
	public List<MsgDTO> outbox(String id) {
		return dao.outbox(id);
	}

	// INBOX
	public List<MsgDTO> inbox(String id) {
		return dao.inbox(id);
	}

	// MSG DETAIL
	public MsgDTO msgDetail(int idx) {
		return dao.msgDetail(idx);
	}

	// DELETE MSG (받은 쪽지 기준)
	public void msgDel(int idx) {
		dao.msgDel(idx);
	}

	// DELETE MSG (보낸 쪽지 기준)
	public void msgDelOut(int idx) {
		dao.msgDelOut(idx);
	}

	// SAVE MSG (받은 쪽지 기준)
	public void msgSave(int idx) {
		dao.msgSave(idx);
	}

	// SAVE MSG (보낸 쪽지 기준)
	public void msgSaveOut(int idx) {
		dao.msgSaveOut(idx);
	}

	// 수신자가 읽었을 때 읽음 처리
	public void readYN(int msg_idx) {
		dao.readYN(msg_idx);
	}

}
