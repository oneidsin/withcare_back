package com.withcare.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.comment.dao.ComDAO;
import com.withcare.comment.dto.ComDTO;

@Service
public class ComService {

	Logger log = LoggerFactory.getLogger(getClass());

	Map<String, Object> result = null;

	@Autowired ComDAO dao;

	// WRITE COMMENT

	public boolean writeCom(ComDTO dto) {

		int row = dao.writeCom(dto);

		return row > 0;
	}

	// UPDATE COMMENT

	public boolean updateCom(ComDTO dto, String id) {
		
		String writerId = dao.writerId(dto.getCom_idx());
		
		if (!writerId.equals(id)) {
			int lv_idx = dao.userLevel(id);
			if (lv_idx != 7) {
				return false;
			}
		}

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
	
	// COMMENT LIST

	public Map<String, Object> comList(int post_idx) {
		
		result = new HashMap<>();
		
		List<ComDTO> comList = dao.comList(post_idx);
		int comments = dao.comCnt(post_idx);
		
		result.put("list", comList);
		result.put("comments", comments);
		
		return result;
	}

}
