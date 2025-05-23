package com.withcare.msg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.msg.dto.MsgDTO;

@Mapper
public interface MsgDAO {

	// Send MSG
	void sendMsg(MsgDTO dto);

	// OUTBOX
	List<MsgDTO> outbox(String id);

	// INBOX
	List<MsgDTO> inbox(String id);

	// MSG DETAIL
	MsgDTO msgDetail(int idx);

	// DEL MSG (IN BOX 기준)
	void msgDel(int idx);

	// DEL MSG (OUT BOX 기준)
	void msgDelOut(int idx);

	// SAVE MSG (IN BOX 기준)
	void msgSave(int idx);

	// SAVE MSG (OUT BOX 기준)
	void msgSaveOut(int idx);

	// 읽음 처리
	void readYN(int msg_idx);

}
