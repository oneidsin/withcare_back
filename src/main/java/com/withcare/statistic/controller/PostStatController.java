package com.withcare.statistic.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.withcare.statistic.dto.PostBestStatDTO;
import com.withcare.statistic.dto.PostStatDTO;
import com.withcare.statistic.service.PostStatService;

@CrossOrigin
@RestController
public class PostStatController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	PostStatService svc;
	
	// 숫자만 세면 되기에 int 로 작업했습니다.
	
		// 주간 게시글 작성 수 카운트
	 @GetMapping("/stat/weekly-count-post")
	    public int getWeeklyPostCount() {
	        return svc.getWeeklyPostCount();
	  }
	
	 // 주간 댓글 작성 수 카운트
	 @GetMapping("/stat/weekly-count-comment")
	    public int getWeeklyCommentCount() {
	        return svc.getWeeklyCommentCount();
	 }
	 
	 // 주간 게시판별 게시글 수와 해당 댓글 수 (최신 글 반응 확인용)
	 @GetMapping("/stat/weekly-post-com")
	    public List<PostStatDTO> getPostAndCom() {
	        return svc.getPostAndCom();
	 }
	 
	 // 주간 인기 게시글 (추천 수, 조회 수 순으로)
	 @GetMapping("/stat/best-post")
	    public List<PostBestStatDTO> bestpostandcom() {
	        return svc.getBestPost();
	 }
	 
	
}
