package com.withcare.comment.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.comment.dao.ComDAO;
import com.withcare.comment.dto.ComDTO;
import com.withcare.post.dto.PostDTO;

@Service
public class ComService {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	HashMap<String, Object> result = null;
	
	@Autowired ComDAO dao;

	// WRITE COMMENT

	public boolean writeCom(ComDTO dto) {
		
		int row = dao.writeCom(dto);
		
		return row > 0;
	}
	
	// UPDATE COMMENT

	public boolean updateCom(ComDTO dto) {
		
		int row = dao.updateCom(dto);
		
		return row > 0;
	}
	
	// DELETE COMMENT

	public boolean delCom(ComDTO dto, String id) {
		
		String writerId = dao.writerId(dto.getCom_idx());
		if (id == null) {
			return false;
		}
		
		if (!writerId.equals(id)) {
			int lv_idx = dao.userLevel(id);
			if (lv_idx != 7) {
				return false;
			}
		}
		
		int row = dao.delCom(dto);
		return row > 0;
	}
	

	

}
