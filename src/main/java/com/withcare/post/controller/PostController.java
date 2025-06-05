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

import com.withcare.board.dto.BoardDTO;
import com.withcare.board.service.BoardService;
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
	@Autowired BoardService boardService;
	
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
		    
	        BoardDTO board = boardService.boardIdx(dto.getBoard_idx());
	        dto.setAnony_yn(board.isAnony_yn());
	        
	        // 프론트엔드에서 전송한 com_yn 값을 유지하도록 코드 제거
	        // 기존 com_yn 값을 로그로 출력
	        log.info("게시글 작성 - 댓글 허용 여부(컨트롤러): {}", dto.getCom_yn());
	        
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
	            try {
	                success = svc.saveFiles(post_idx, files); // 작성자랑 login 아이디가 맞으면 파일 저장
	                if (!success) {
	                    result.put("message", "파일 업로드 실패: 이미지는 최대 10장까지만 업로드할 수 있습니다.");
	                }
	            } catch (IllegalArgumentException e) {
	                // 파일 크기, 타입 등의 검증 실패
	                success = false;
	                result.put("message", e.getMessage());
	            } catch (Exception e) {
	                // 기타 예외
	                log.error("파일 업로드 중 오류", e);
	                success = false;
	                result.put("message", "파일 업로드 중 오류가 발생했습니다.");
	            }
	        } else {
	            // 작성자 불일치
	            success = false;
	            result.put("message", "작성자만 파일을 업로드할 수 있습니다.");
	        }
	    } else {
	    	success = false;
	    	result.put("message", "로그인이 필요합니다.");
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
		    	try {
		    	    success = svc.updateFiles(post_idx, files, keepFileIdx);
		    	    if (!success) {
		    	        result.put("message", "파일 업데이트 실패: 이미지는 최대 10장까지만 업로드할 수 있습니다.");
		    	    }
		    	} catch (IllegalArgumentException e) {
		    	    // 파일 크기, 타입 등의 검증 실패
		    	    success = false;
		    	    result.put("message", e.getMessage());
		    	} catch (Exception e) {
		    	    // 기타 예외
		    	    log.error("파일 업데이트 중 오류", e);
		    	    success = false;
		    	    result.put("message", "파일 업데이트 중 오류가 발생했습니다.");
		    	}
			} else {
				success = false;
				result.put("message", "작성자만 파일을 수정할 수 있습니다.");
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
	    int userLv = 0;
	        
	    try {
	        loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
	        userLv = svc.userLevel(loginId);
	    } catch (Exception e) {
	        // 토큰 없거나 잘못됐으면 그냥 로그인 false 처리
	        login = false;
	    }
	        
	    if (loginId != null && !loginId.isEmpty()) {
	        login = true;
	    }

	    // 게시판의 레벨 제한 확인
	    int boardIdx = svc.getBoardIdx(post_idx);
	    int boardLv = boardService.boardLevel(boardIdx);
	    
	    if (userLv < boardLv) {
	        result.put("success", false);
	        result.put("message", "권한이 없습니다.");
	        return result;
	    }

	    Map<String, Object> detailResult = svc.postDetail(post_idx, true, userLv);
	    result.putAll(detailResult);
	    result.put("loginYN", login);
	    return result;
	}
	
	// 조회수 증가 없이 상세 정보만 조회
	@GetMapping("/post/detail/{post_idx}")
	public Map<String, Object> postDetailNoHit(
	        @PathVariable int post_idx,
	        @RequestHeader Map<String, String> header) {
	       
	    Map<String, Object> result = new HashMap<>();
	    String loginId = null;
	    int userLv = 0;
	       
	    try {
	        loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
	        userLv = svc.userLevel(loginId);
	    } catch (Exception e) {
	        // 무시
	    }

	    // 게시판의 레벨 제한 확인
	    int boardIdx = svc.getBoardIdx(post_idx);
	    int boardLv = boardService.boardLevel(boardIdx);
	    
	    if (userLv < boardLv) {
	        result.put("success", false);
	        result.put("message", "권한이 없습니다.");
	        return result;
	    }

	    Map<String, Object> detailResult = svc.postDetail(post_idx, false, userLv);
	    result.putAll(detailResult);
	    return result;
	}
	
	// 게시글 리스트 (GetMapping)
	@GetMapping("/post/list/{page}")
	public Map<String, Object> postList(
	        @PathVariable int page,
	        @RequestParam int board_idx,
	        @RequestParam(defaultValue = "latest") String sort,
	        @RequestParam(required = false) String searchType,
	        @RequestParam(required = false) String keyword,
	        @RequestHeader Map<String, String> header) {

	    Map<String, Object> result = new HashMap<>();
	    String loginId = null;
	    boolean login = false;
	    int userLv = 0;

        try {
            loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
            userLv = svc.userLevel(loginId);
        } catch (Exception e) {
            // 토큰 없거나 유효하지 않은 경우, 그냥 로그인 false 처리하고 진행
        }
        
        if (loginId != null && !loginId.isEmpty()) {
            login = true;
        }
        
        Map<String, Object> listResult;
        // 관리자(userLv=7)인 경우 블라인드 처리된 게시글도 표시
        if (userLv == 7) {
            listResult = svc.postListForAdmin(page, board_idx, sort, searchType, keyword);
        } else {
            listResult = svc.postList(page, board_idx, sort, searchType, keyword);
        }
        
	    result.putAll(listResult);
	    result.put("success", true);
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
	
	@GetMapping("/post/like/status/{post_idx}")
	public Map<String, Object> getLikeStatus(
			@PathVariable int post_idx,
			@RequestHeader Map<String, String> header) {
		
		Map<String, Object> result = new HashMap<>();
		String loginId = null;
		boolean login = false;
		
		try {
			loginId = (String) JwtUtils.readToken(header.get("authorization")).get("id");
		} catch (Exception e) {
			result.put("success", false);
			result.put("loginYN", false);
			return result;
		}
		
		if (loginId != null && !loginId.isEmpty()) {
			login = true;
			List<Integer> likeTypes = svc.getLikeStatus(loginId, post_idx);
			int likeStatus = 0;
			if (likeTypes != null && !likeTypes.isEmpty()) {
				likeStatus = likeTypes.get(0);
			}
			result.put("likeStatus", likeStatus);
		}
		
		result.put("success", true);
		result.put("loginYN", login);
		
		return result;
	}
}
