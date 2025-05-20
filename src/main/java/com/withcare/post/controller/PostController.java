package com.withcare.post.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;
import com.withcare.post.service.PostService;

@CrossOrigin
@RestController
public class PostController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	HashMap<String, Object> result = null;
	
	@Autowired PostService svc;
	
	// 게시글 작성 (PostMapping)
	@PostMapping("/post/write")
	public Map<String, Object> postWrite(
	        @RequestBody PostDTO dto, // 이거랑 multipartFile 이랑 자꾸 같이 쓰지 말라면서 겁나 갈궈서 그냥 파일 첨부랑 게시글 작성이랑 분리했습니당...ㅠ
	        @RequestHeader Map<String, String> header) {

	    Map<String, Object> result = new HashMap<>();

	    dto.setId(header.get("id")); // 사용자 ID 설정

	    boolean success = svc.postWrite(dto);
	    result.put("idx", dto.getPost_idx()); // 작성한 게시글 idx 가져오기
	    result.put("success", success); // 성공 여부

	    return result;
	}
	
	// 파일 첨부
	@PostMapping("/post/upload-files")
	public Map<String, Object> uploadFiles(
	        @RequestParam("post_idx") int postIdx,
	        @RequestParam("files") MultipartFile[] files) {

	    Map<String, Object> result = new HashMap<>();

	    boolean success = svc.saveFiles(postIdx, files); // 파일 저장하기
	    result.put("success", success);

	    return result;
	}
	
	// 게시글 수정 (PutMapping)
	@PutMapping("/post/update")
	public Map<String, Object>postUpdate(
	        PostDTO dto,
			@RequestParam(value = "files", required = false) MultipartFile[] files,
		    @RequestParam(value = "keepFileIds", required = false) List<String> keepFileIdx,
			@RequestHeader Map<String, String>header){
		
	    if (files == null) {
	        files = new MultipartFile[] {};
	    }
		
	    log.info("수정 대상 파일 수: " + files.length);
	    
	    String userId = header.get("id");
	    
	    for (MultipartFile file : files) {
			log.info("file name : "+file.getOriginalFilename());
		}
		
	    result = new HashMap<String, Object>();
	    
		boolean success = svc.postUpdate(dto, userId,files,keepFileIdx);
		result.put("idx", dto.getPost_idx());
		result.put("success", success);
		
	    if (success) {
	        List<Map<String, String>> photoList = svc.fileList(dto.getPost_idx());
	        result.put("photos", photoList);
	    }
		return result;
		
	}
	
	// 게시글 삭제
	@PutMapping("/post/delete")
	public Map<String, Object>postDelete(
			@RequestBody PostDTO dto,
			@RequestHeader Map<String, String>header){

	    String userId = header.get("id");
	    
		result = new HashMap<String, Object>();
		boolean success = svc.postDelete(dto, userId);
		result.put("idx", dto.getPost_idx());
		result.put("success", success);
		
		return result;
	}
	
	// 게시글 상세보기 (GetMapping)
	@GetMapping("/post/detail/{post_idx}")
	public Map<String, Object>postDetail(
			@PathVariable int post_idx,
			@RequestHeader Map<String, String>header){

		return svc.postDetail(post_idx, true);
	}
	
	// 게시글 리스트 (GetMapping)
	@GetMapping("/post/list/{page}")
	public Map<String, Object> postList(
	        @PathVariable int page,
	        @RequestParam int board_idx,
	        @RequestHeader Map<String, String> header) {

	    Map<String, Object> result = new HashMap<>();
	    result = svc.postList(page, board_idx);

	    return result;
	}
	
	// 게시글 추천, 비추천 (PostMapping)
	@PostMapping("/post/like")
	public Map<String, Object> postLike(
	        @RequestBody LikeDislikeDTO dto,
	        @RequestHeader Map<String, String> header) {
	    
	    Map<String, Object> result = new HashMap<>();
	    dto.setId(header.get("id"));

	    boolean success = svc.handleLike(dto);
	    result.put("success", success);
	    return result;
	}
	
	// 게시글 동록된 이미지 리스트 조회
	@GetMapping("/file/list/{post_idx}")
	public List<Map<String, String>> fileList(@PathVariable int post_idx) {
	    return svc.fileList(post_idx); // 정상적으로 동작하는 기존 서비스 메서드
	}
	
	@DeleteMapping("/file/{file_idx}")
	public Map<String, Object> fileDelete(
	    @PathVariable String file_idx,
	    @RequestHeader Map<String, String> header) {

	    Map<String, Object> result = new HashMap<>();
	    String userId = header.get("id");

	    boolean success = svc.fileDelete(file_idx, userId);
	    result.put("success", success);

	    return result;
	}
	
}
