package com.withcare.main.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainDAO {

	List<?> ranking();

	

}
