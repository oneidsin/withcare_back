package com.withcare.comment.dao;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.comment.dto.ComDTO;

@Mapper
public interface ComDAO {

	// WRITE COMMENT
	int writeCom(ComDTO dto);

	// UPDATE COMMENT
	int updateCom(ComDTO dto);

	String writerId(int com_idx);

	int userLevel(String id);

	// DELETE COMMENT
	int delCom(ComDTO dto);

}
