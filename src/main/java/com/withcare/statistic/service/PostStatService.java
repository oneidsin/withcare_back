package com.withcare.statistic.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.board.dto.BoardDTO;
import com.withcare.statistic.dao.PostStatDAO;

@Service
public class PostStatService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	PostStatDAO dao;

	public int getPostWeeklyCount() {
		return dao.getPostWeeklyCount();
	}

	public int getCommentWeeklyCount() {
		return dao.getCommentWeeklyCount();
	}

	public double getPostAndCom() {
		return dao.getPostAndCom();
	}

	public List<BoardDTO> getBoardPostAndCom() {
		return dao.getBoardPostAndCom();
	}
}
