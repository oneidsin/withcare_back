package com.withcare.report.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.report.dao.ReportDAO;

@Service
public class ReportService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired ReportDAO dao;
	
}
