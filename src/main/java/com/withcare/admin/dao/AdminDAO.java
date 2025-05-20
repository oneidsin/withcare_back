package com.withcare.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.admin.dto.AdminMemberDTO;

@Mapper
public interface AdminDAO {

	int userLevel(String loginId);
	
	int levelUpdate(Map<String, Object> params);

	List<AdminMemberDTO> adminMemberList(Map<String, Object> params);


}
