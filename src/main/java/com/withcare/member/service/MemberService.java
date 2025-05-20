package com.withcare.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.member.dao.JoinDAO;
import com.withcare.member.dao.MemberDAO;

@Service
public class MemberService {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired MemberDAO dao;


	public boolean delete(String id) {
	    int row = dao.delete(id);
	    return row > 0;
	}


}
