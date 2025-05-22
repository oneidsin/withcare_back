package com.withcare.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.comment.dto.ComDTO;
import com.withcare.comment.dto.MenDTO;

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

	// COMMENT LIST 
	List<ComDTO> comList(int post_idx);
	
	// COUNT COMMENT
	int comCnt(int post_idx);

	// WRITE MENTION
	void writeMention(MenDTO menDto);

	// GET ID LIST
	List<String> selectId();

	String getPostWriterId(int post_idx);
}
