package com.withcare.search.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.search.dao.SearchDAO;

@Service
public class SearchService {

	@Autowired
	SearchDAO dao;

	Logger log = LoggerFactory.getLogger(getClass());

}
