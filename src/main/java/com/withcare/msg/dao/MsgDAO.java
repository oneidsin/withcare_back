package com.withcare.msg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.msg.dto.MsgDTO;

@Mapper
public interface MsgDAO {

	//Send MSG
	void sendMsg(MsgDTO dto);

	// OUTBOX
	List<MsgDTO> outbox(String id);

	// INBOX
	List<MsgDTO> inbox(String id);

	// MSG DETAIL
	MsgDTO msgDetail(int idx);

}
