package com.withcare.report.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlockDAO {

	int block(Map<String, Object> params);

	Map<String, Object> blockList(String id);
}
