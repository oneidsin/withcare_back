package com.withcare.statistic.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.statistic.dto.PostStatDTO;
import com.withcare.statistic.service.PostStatService;

@CrossOrigin
@RestController
public class PostStatController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	PostStatService svc;
	
		// 주간 게시글 작성 수
	 @GetMapping("/stat/post/weekly_count")
	    public int getPostWeeklyCount() {
	        return svc.getPostWeeklyCount();
	  }
	
	 // 주간 댓글 작성 수
	 @GetMapping("/stat/comment/weekly_count")
	    public int getCommentWeeklyCount() {
	        return svc.getCommentWeeklyCount();
	 }
	 
	 // 주간 게시판별 게시글 수와 해당 댓글 수 (최신 글 반응 확인용)
	 @GetMapping("/stat/postandcom")
	    public List<PostStatDTO> getPostAndCom() {
	        return svc.getPostAndCom();
	 }
	 
	
}
