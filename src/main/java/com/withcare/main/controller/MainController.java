package com.withcare.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.main.dao.MainDAO;


@CrossOrigin
@RestController
public class MainController {
	
	@Autowired MainDAO dao;
	
	// 랭킹 명단 불러오는 명단
    @GetMapping("/ranking")
    public List<?> ranking() {
        return dao.ranking();
    }
    
}


