package com.withcare.report.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.report.service.ReportService;

@CrossOrigin
@RestController
public class ReportController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired ReportService svc;
	
}
