package com.withcare.post.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.withcare.board.dao.BoardDAO;
import com.withcare.post.dao.PostDAO;
import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;

@Service
public class PostService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	HashMap<String, Object> result = null;
	
	private int post_count = 5;
	
	@Autowired PostDAO dao;
	@Autowired BoardDAO boardDao;
	
	@Value("${file.upload-dir}")
	private String uploadDir; // 우선 user.home 에 있는 uploads 폴더로 경로 지정해뒀습니다. (서혜 언니는 다른 경로 지정 필수)
	
	public boolean postWrite(PostDTO dto) {
	    // 게시판 com_yn 가져오기
	    boolean boardComYn = boardDao.boardComYn(dto.getBoard_idx());

	    dto.setCom_yn(boardComYn);

	    int row = dao.postWrite(dto);
	    return row > 0;
	}

	public boolean saveFiles(int post_idx, MultipartFile[] files) {
		List<String> savedFileNames = new ArrayList<>();
		boolean success = true;

        try {
	        for (MultipartFile file : files) {
	        	if (file.isEmpty()) continue; // file 이 없어도 에러 안나게 해놓은 거
	        	
	            if (file.getSize() > 10 * 1024 * 1024) { // 10MB 제한
	                throw new IllegalArgumentException("파일 사이즈 초과");
	            }
	            
	            // MIME 타입 검사
	            if (!file.getContentType().startsWith("image/")) {
	                throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
	            }
	
	            // 확장자 검사 (jpg, jpeg, png만 허용)
	            String origin_name = file.getOriginalFilename();
	            if (origin_name == null || origin_name.lastIndexOf(".") == -1) {
	                throw new IllegalArgumentException("파일 이름이 잘못되었습니다.");
	            }
	
	            String ext = origin_name.substring(origin_name.lastIndexOf(".") + 1).toLowerCase(); // 작성자가 대문자로 넣으면 그거 변환
	            if (!ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png")) {
	                throw new IllegalArgumentException("jpg, jpeg, png 확장자만 업로드 가능합니다.");
	            }
	
	            String extension = origin_name.substring(origin_name.lastIndexOf(".")); // 확장자 "." 에서 자르기
	            
	            // UUID로 파일명 생성
	            String savedName = UUID.randomUUID().toString() + extension;
	            
	                // 파일 저장 경로 생성
	                Path path = Paths.get(uploadDir, savedName);
	
	                // 폴더가 없으면 생성
	                Files.createDirectories(path.getParent());
	
	                // 실제 파일 저장
	                file.transferTo(path.toFile());
	
	                // DB에 파일 URL 저장
	                Map<String, Object> param = new HashMap<>();
	                param.put("post_idx", post_idx);
	                param.put("file_url", savedName);  // 혹은 full path로 저장하고 싶으면 변경
	
	                dao.fileInsert(param);
	                savedFileNames.add(savedName); // 성공한 파일만 저장
	                
	            } 
	        	
	        	return true; // 모두 성공했을 경우
	        	
        }catch (Exception e) {
	                e.printStackTrace();

            // 저장된 파일 시스템에서 삭제
            for (String savedName : savedFileNames) {
                try {
                    Path path = Paths.get(uploadDir, savedName);
                    Files.deleteIfExists(path);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    log.error("파일 시스템 삭제 실패: " + savedName, ex);
                }

                // DB에서도 삭제
                dao.fileDelete(savedName); // file_url 기준으로 삭제
            }
            return false;
        }
    
    }

	public boolean postUpdate(PostDTO dto, String userId, MultipartFile[] files, List<String> keepFileIdx) {
	    String writerId = dao.postWriter(dto.getPost_idx());
	    if (writerId == null || !writerId.equals(userId)) { // 작성자 id 가 없거나 사용자랑 다르면 update 실행 안돼요. (관리자 수정은 필요 없다고 해서 뺐습니다.)
	        return false;
	    }

	    int row = dao.postUpdate(dto);

	    if (row > 0) {
	        if ((files != null && files.length > 0) || (keepFileIdx != null && !keepFileIdx.isEmpty())) {
	            // 기존 파일 리스트 조회 및 삭제 (keepFileIdx를 기준으로 삭제)
	            List<Map<String, String>> currentFiles = dao.fileList(dto.getPost_idx());

	            for (Map<String, String> file : currentFiles) {
	            	String fileIdx = String.valueOf(file.get("file_idx")); // String으로 변환시켜줌
	                if (keepFileIdx == null || !keepFileIdx.contains(fileIdx)) { // 위와 동일
	                    dao.fileDelete(fileIdx);
	                    deleteFileIdx(file.get("file_url"));
	                }
	            }
	        }

	        // 새 파일 저장
	        if (files != null && files.length > 0) {
	            saveFiles(dto.getPost_idx(), files);
	        }
	    }
	    return row > 0;
	}

	private void deleteFileIdx(String savedName) {
	    try {
	        java.nio.file.Path path = java.nio.file.Paths.get(uploadDir, savedName); // 파일 삭제하기
	        java.nio.file.Files.deleteIfExists(path);
	    } catch (Exception e) {
	        log.error("파일 삭제 실패: " + savedName, e);
	    }
	}

	public boolean postDelete(PostDTO dto, String userId) {
		
	    // 1) 게시글 작성자 조회
	    String writerId = dao.postWriter(dto.getPost_idx());
	    if (writerId == null) {
	        return false;
	    }
	    
	    // 2) 작성자 아니면 관리자 여부 체크
	    if (!writerId.equals(userId)) {
	        int lv_idx = dao.userLevel(userId);
	        if (lv_idx != 7) {
	            // 작성자도 아니고 관리자도 아니면 삭제 불가
	            return false;
	        }
	    }
		
		int row = dao.postDelete(dto);
		return row>0;
	}

	public Map<String, Object> postDetail(int post_idx, boolean up) {
		result = new HashMap<String,Object>();
		
		if (up) {
			dao.upHit(post_idx); // 상세보기 시 조회수 증가
		}
		
	    PostDTO dto = dao.postDetail(post_idx);
	    result.put("post", dto);
	    result.put("likes", dao.likeCnt(post_idx)); // 추천 수
	    result.put("dislikes", dao.dislikeCnt(post_idx)); // 비추천 수
	    List<Map<String, String>> photos = dao.fileList(post_idx);
	    result.put("photos", photos);

	    return result;
	}
	
	public Map<String, Object> postList(int page, int board_idx) {
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("page", page);
	    int offset = (page - 1) * post_count; // 페이지 시작 위치 계산

	    List<PostDTO> postList = dao.postList(offset, post_count, board_idx); // 게시글 목록, 한 페이지 당 보여줄 게시글 수, 게시판 번호
	    int totalPosts = dao.postPages(board_idx);
	    int totalPages = (int) Math.ceil((double) totalPosts / post_count); // 한 페이지당 보여줄 게시글 개수로 나눈 후 올림 (double 값 정수 변환)
	    
	    List<Map<String, Object>> postMapList = new ArrayList<>();

	    for (PostDTO dto : postList) {
	        Map<String, Object> postMap = new HashMap<>();
	        postMap.put("post", dto);
	        postMap.put("likes", dao.likeCnt(dto.getPost_idx()));
	        postMap.put("dislikes", dao.dislikeCnt(dto.getPost_idx()));
	        List<Map<String, String>> photos = dao.fileList(dto.getPost_idx());
	        postMap.put("photos", photos);
	        postMapList.add(postMap);
	    }
	    
	    result.put("list", postMapList);
	    result.put("totalPages", totalPages);
	    result.put("totalPosts", totalPosts);
	    result.put("page", page);

	    return result;
	}

	public boolean handleLike(LikeDislikeDTO dto) {
		String id = dto.getId(); // 같은 ID 인지 확인하려고 id 값 dto 에서 가져옴.
	    int post_idx = dto.getPost_idx();
	    int newType = dto.getLike_type(); // 추천 상태 확인

	    Integer currentType = dao.LikeType(id, post_idx); // Integer 가 아니라 int 면 null 값 지정 못함. (아무것도 안 눌렀을 때 상태)
	    
	    if (currentType == null) { // 처음에 null 이면 0 으로 변환시켜서 int 타입으로 설정
	        currentType = 0;
	    }
	    
	    if (currentType == newType) {
	        // 같은 상태 → 취소
	        return dao.likeDelete(id, post_idx) > 0;
	    } else if (currentType == 0) {
	        // 처음 → 삽입
	        return dao.likeInsert(dto) > 0;
	    } else {
	        // 다른 상태 → 업데이트
	        return dao.likeUpdate(dto) > 0;
	    } 
	}

	public List<Map<String, String>> fileList(int post_idx) {
		return dao.fileList(post_idx);
	}

	public boolean fileDelete(String file_idx, String userId) {
	    // 1) 파일 정보 조회
	    Map<String, String> fileInfo = dao.fileInfo(file_idx);
	    if (fileInfo == null) {
	        return false; // 파일 존재하지 않음
	    }
	    int post_idx = Integer.parseInt(fileInfo.get("post_idx"));
	    
	    // 2) 게시글 작성자 조회
	    String writerId = dao.postWriter(post_idx);
	    if (writerId == null) {
	        return false;
	    }
	    
	    // 3) 권한 체크 (작성자 또는 관리자만 삭제 가능)
	    if (!writerId.equals(userId)) {
	        int lv_idx = dao.userLevel(userId);
	        if (lv_idx != 7) { // 관리자 권한 레벨 (예시)
	            return false;
	        }
	    }
	    
	    // 파일 삭제
	    int row = dao.fileDelete(file_idx);
	    if (row > 0) {
	    	// 실제 저장된 파일 삭제
	        if (fileInfo != null) {
	            deleteFileIdx(fileInfo.get("file_url"));
	        }
	    }
	    return row > 0;
	    
	}
}
