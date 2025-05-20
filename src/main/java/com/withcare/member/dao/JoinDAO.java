package com.withcare.member.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JoinDAO {

	int overlay(String id);

	int join(Map<String, String> params);

}
