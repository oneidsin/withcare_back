package com.withcare.post.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.withcare.util.JwtToken.JwtUtils;

@CrossOrigin
@RestController
public class PostController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	HashMap<String, Object> result = null;
	
	@Autowired PostService svc;
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	// 토큰 수정 필요
	// 게시글 작성 (PostMapping) (파일 첨부 별도 처리함)
	@PostMapping("/post/write")
	public Map<String, Object> postWrite(
	        @RequestBody PostDTO dto, // 파일 첨부 및 게시글 별도 처리
	        @RequestHeader Map<String, String> header) {
		
        // 사용할 변수 초기화
        Map<String, Object> result = new HashMap<>();
        String loginId = null;
        boolean login = false;
        boolean success = false;
        
        try {
    	    // 토큰 발급
    	    loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		} catch (Exception e) {
			// 안되면 로그인 false
			result.put("success", false);
			result.put("loginYN", false);
			return result;
		}
        
        
	    if (loginId != null && !loginId.isEmpty()) { // loginId 가 비어있으면
	    	login = true;
		    dto.setId(loginId); // 게시글에 작성자 ID 설정
	        success = svc.postWrite(dto); // 게시글 작성 서비스 호출
	        result.put("idx", dto.getPost_idx()); // 작성한 게시글 idx 가져오기
	    }
	    
	    result.put("success", success); // 성공 여부
	    result.put("loginYN", login);

	    return result;
	}
	
	// 파일 첨부
	@PostMapping("/post/file/upload")
	public Map<String, Object> fileUpload(
			@RequestParam int post_idx,
	        @RequestParam MultipartFile[] files,
	        @RequestHeader Map<String, String> header) {

        Map<String, Object> result = new HashMap<>();
        String loginId = null;
        boolean login = false;
        boolean success = false;

        try {
    	    loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		} catch (Exception e) {
			result.put("success", success);
			result.put("loginYN", false);
			return result;
		}
	    
	    if (loginId != null && !loginId.isEmpty()) {
	    	login  = true;
	    	String postWriter = svc.postWriter(post_idx);
	    	
	        if (postWriter != null && loginId.equals(postWriter)) {
	            success = svc.saveFiles(post_idx, files); // 작성자랑 login 아이디가 맞으면 파일 저장
	        } else {
	            // 작성자 불일치
	            success = false;
	        }
	    }else {
	    	success = false;
	    }
	    
	    result.put("success", success);
	    result.put("loginYN", login);
	    
	    return result;
	}
	

	// 게시글 수정 (PutMapping)
	@PutMapping("/post/update")
	public Map<String, Object> postUpdate(
	        @RequestBody PostDTO dto,  // JSON 바디 바인딩하려면 @RequestBody 필수!
	        @RequestHeader Map<String, String> header) {

        Map<String, Object> result = new HashMap<>();
        String loginId = null;
        boolean login = false;
        boolean success = false;

	    // 토큰에서 로그인 ID 꺼내기
        try {
    	    loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		} catch (Exception e) {
			result.put("success", false);
			result.put("loginYN", false);
			return result;
		}


	    if (loginId != null && !loginId.isEmpty()) {
	        login = true;
	        String postWriter = svc.postWriter(dto.getPost_idx());
	        if (postWriter != null && loginId.equals(postWriter)) {
	        	 // 파일은 /post/file/update 에서 처리되므로 여기서는 파일에 대해 null 전달
	        	success = svc.postUpdate(dto);
			}else {
				success = false;
			}
	    }

	    result.put("idx", dto.getPost_idx());
	    result.put("success", success);
	    result.put("loginYN", login);

	    if (success) {
	        List<Map<String, String>> photoList = svc.fileList(dto.getPost_idx());
	        result.put("photos", photoList);
	    }

	    return result;
	}
	
	// 파일 수정 (기존 파일 삭제 및 새 파일 추가)
	@PostMapping("/post/file/update")
	public Map<String, Object> uploadFiles(
	        @RequestParam("post_idx") int post_idx,
	        @RequestParam(value = "files", required = false) MultipartFile[] files,
	        @RequestParam(value = "keepFileIdx", required = false)List<String>keepFileIdx,
	        @RequestHeader Map<String, String>header) {

        Map<String, Object> result = new HashMap<>();
        String loginId = null;
        boolean login = false;
        boolean success = false;
	    
        try {
    	    loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		} catch (Exception e) {
			result.put("success", false);
			result.put("loginYN", false);
			return result;
		}

	    if (loginId != null) {
	        login = true;
	        String postWriter = svc.postWriter(post_idx);
	        if (postWriter != null && loginId.equals(postWriter)) { // 작성자 ID랑 로그인 ID 가 동일할 때만
		    	success = svc.updateFiles(post_idx, files, keepFileIdx);
			}else {
				success = false;
			}
	    }
	    result.put("loginYN", login);
	    result.put("success", success);
	    
        // 선택적으로 업데이트된 파일 목록 반환
        if (success) {
             List<Map<String, String>> photoList = svc.fileList(post_idx);
             result.put("photos", photoList);
        }
	    return result;
	}
	

	// 게시글 삭제
	@PutMapping("/post/delete")
	public Map<String, Object>postDelete(
			@RequestBody PostDTO dto,
			@RequestHeader Map<String, String>header){

        Map<String, Object> result = new HashMap<>();
        String loginId = null;
        boolean login = false;
        boolean success = false;
        
        try {
    	    loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		} catch (Exception e) {
			result.put("success", false);
			result.put("loginYN", false);
			return result;
		}
        
	    if (loginId != null) {
	        login = true;
	        String postWriter = svc.postWriter(dto.getPost_idx());
            boolean authorized = false;
            
	    	if (postWriter != null && loginId.equals(postWriter)) {
	            authorized = true;
			}else if (postWriter != null) { // 게시글은 존재하지만 사용자가 작성자가 아닌 경우, 관리자인지 확인 (관리자 게시판 삭제 권한 때문에)
				int userLevel = svc.userLevel(loginId);
				if (userLevel == 7) {
					authorized = true;
				}
			}
            if (authorized) {
                success = svc.postDelete(dto);
            } else {
            	success = false;
            }
	    }

		result.put("idx", dto.getPost_idx());
		result.put("success", success);
	    result.put("loginYN", login);
	    
		return result;
	}
	
	// 게시글 상세보기 (GetMapping) 조회수 증가
	   @GetMapping("/post/detail/hitup/{post_idx}")
	   public Map<String, Object>postDetail(
	         @PathVariable int post_idx,
	         @RequestHeader Map<String, String>header){
		   
	        Map<String, Object> result = new HashMap<>();
	        String loginId = null;
	        boolean login = false;
	        boolean success = false;
	        
	        try {
	            loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
	        } catch (Exception e) {
	            // 토큰 없거나 잘못됐으면 그냥 로그인 false 처리하고 게시글 상세보기는 진행
	        	login = false;
	        }
	        
	        if (loginId != null && !loginId.isEmpty()) {
				login = true;
			}
	        return svc.postDetail(post_idx, true); // true는 조회수 증가
	   }
	   
	   // 조회수 증가 없이 상세 정보만 조회
	   @GetMapping("/post/detail/{post_idx}")
	   public Map<String, Object> postDetailNoHit(
	           @PathVariable int post_idx,
	           @RequestHeader Map<String, String> header) {
	       
	       String loginId = null;
	       try {
	           loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
	       } catch (Exception e) {
	           // 무시
	       }

	       return svc.postDetail(post_idx, false); // 조회수 증가 X
	   }
	
	// 게시글 리스트 (GetMapping)
	@GetMapping("/post/list/{page}")
	public Map<String, Object> postList(
	        @PathVariable int page,
	        @RequestParam int board_idx,
	        @RequestParam(defaultValue = "latest") String sort, // 추가 (추천순, 최신순 선택해서 보여주기 위해서)
	        @RequestParam(required = false) String searchType,
	        @RequestParam(required = false) String keyword,
	        @RequestHeader Map<String, String> header) {

	    Map<String, Object> result = new HashMap<>();
	    String loginId = null;
	    boolean login = false;

        try {
            loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
        } catch (Exception e) {
            // 토큰 없거나 유효하지 않은 경우, 그냥 로그인 false 처리하고 진행 (게시글 리스트는 그냥 보입니다.)
        }
        
        if (loginId != null && !loginId.isEmpty()) {
            login = true;
        }
        
	    Map<String, Object> listResult = svc.postList(page, board_idx, sort, searchType, keyword);
	    result.putAll(listResult);
	    
	    result.put("loginYN", login);
	    result.put("loginId", loginId);
	    return result;
	}
	
	// 게시글 추천, 비추천 (PostMapping)
	@PostMapping("/post/like")
	public Map<String, Object> postLike(
	        @RequestBody LikeDislikeDTO dto,
	        @RequestHeader Map<String, String> header) {
	    
        Map<String, Object> result = new HashMap<>();
        String loginId = null;
        boolean login = false;
        boolean success = false;
        
        try {
    	    loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		} catch (Exception e) {
			result.put("success", false);
			result.put("loginYN", false);
			return result;
		}
        
	    if (loginId != null && !loginId.isEmpty()) {
	    	login = true;
		    dto.setId(loginId);
	    	success = svc.handleLike(dto);
	    }
	    
	    result.put("loginYN", login);
	    result.put("success", success);
	    
	    return result;
	}
	
	// 게시글 등록된 이미지 리스트 조회 (얘 없으면 위에서 파일 수정 시에 파일 못 불러와요)
	@GetMapping("/file/list/{post_idx}")
	public List<Map<String, String>> fileList(@PathVariable int post_idx) {
	    return svc.fileList(post_idx); // 정상적으로 동작하는 기존 서비스 메서드
	}
	
	@GetMapping("/file/{file_url}")
	public ResponseEntity<Resource> getImage(@PathVariable String file_url) {
	    try {
	        Path path = Paths.get(uploadDir, file_url);
	        Resource resource = new UrlResource(path.toUri());

	        if (!resource.exists() || !resource.isReadable()) {
	            return ResponseEntity.notFound().build();
	        }

	        String contentType = Files.probeContentType(path);
	        if (contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_TYPE, contentType)
	                .body(resource);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
}
