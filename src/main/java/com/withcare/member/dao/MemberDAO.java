package com.withcare.member.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {

	int delete(String id);

}
