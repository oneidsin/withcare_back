package com.withcare.profile.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.profile.dto.ProfileDTO;

@Mapper
public interface ProfileDAO {

	int saveProfile(Map<String, Object> map);
	
	ProfileDTO getProfileById(String id);

	int updateProfile(ProfileDTO dto);

	int increaseAccessCount(String id);

	void insertProfile(String id);
	
	
}
