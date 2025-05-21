package com.withcare.statistic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.statistic.dao.StatDAO;

@Service
public class StatService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired StatDAO dao;
}
