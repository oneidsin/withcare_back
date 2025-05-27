package com.withcare.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.profile.dto.CancerDTO;
import com.withcare.profile.dto.StageDTO;

@Mapper
public interface JoinDAO {

	int overlay(String id);

	int join(Map<String, String> params);

	List<CancerDTO> cancer();

	List<StageDTO> stage();

}
