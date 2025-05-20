package com.withcare.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.admin.service.AdminService;

@CrossOrigin
@RestController
public class AdminController {
	
	@Autowired AdminService svc;
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	
	
	
}
