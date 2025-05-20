package com.withcare.msg.dao;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.msg.dto.MsgDTO;

@Mapper
public interface MsgDAO {

	//Send MSG
	void sendMsg(MsgDTO dto);

}
