package com.withcare.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.admin.dao.AdminDAO;
import com.withcare.admin.dto.AdminMemberDTO;
import com.withcare.admin.dto.AdminMemberDetailDTO;
import com.withcare.post.dto.PostDTO;

@Service
public class AdminService {

	@Autowired AdminDAO dao;
	
	public int userLevel(String loginId) {
		return dao.userLevel(loginId);
	}

	
	public boolean levelUpdate(Map<String, Object> params) {
		int row = dao.levelUpdate(params);
		return row>0;
	}


	public List<AdminMemberDTO> adminMemberList(Map<String, Object> params) {
		return dao.adminMemberList(params);
	}


	public AdminMemberDetailDTO adminMemberDetail(String targetId) {
		return dao.adminMemberDetail(targetId);
	}


	public List<PostDTO> adminMemberPost(String targetId) {
		return dao.adminMemberPost(targetId);
	}

}
