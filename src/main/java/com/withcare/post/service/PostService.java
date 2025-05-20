package com.withcare.post.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	private final String uploadDir = System.getProperty("user.home") + "/uploads";
	
	public boolean postWrite(PostDTO dto) {
	    // 게시판 com_yn 가져오기
	    boolean boardComYn = boardDao.boardComYn(dto.getBoard_idx());

	    dto.setCom_yn(boardComYn);

	    int row = dao.postWrite(dto);
	    return row > 0;
	}

	public boolean saveFiles(int post_idx, MultipartFile[] files) {
		
		boolean success = true;
		
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            try {
                String originalName = file.getOriginalFilename();
                String extension = "";

                if (originalName != null && originalName.contains(".")) {
                    extension = originalName.substring(originalName.lastIndexOf("."));
                }

                // UUID로 파일명 생성
                String savedName = java.util.UUID.randomUUID().toString() + extension;

                // 파일 저장 경로 생성
                java.nio.file.Path path = java.nio.file.Paths.get(uploadDir, savedName);

                // 폴더가 없으면 생성
                java.nio.file.Files.createDirectories(path.getParent());

                // 파일 저장
                file.transferTo(path.toFile());

                // DB에 파일 URL 저장
                Map<String, Object> param = new HashMap<>();
                param.put("post_idx", post_idx);
                param.put("file_url", savedName);  // 혹은 full path로 저장하고 싶으면 변경

                dao.fileInsert(param);

            } catch (Exception e) {
                e.printStackTrace();
                success = false;
                
                // 파일 저장 실패시에도 무조건 실패처리할지, 로그만 남기고 진행할지 정책에 따라 조정 필요
            }
        }
        return success;
    }

	public boolean postUpdate(PostDTO dto, String userId, MultipartFile[] files, List<String> keepFileIdx) {
	    String writerId = dao.postWriter(dto.getPost_idx());
	    if (writerId == null || !writerId.equals(userId)) {
	        return false;
	    }

	    int row = dao.postUpdate(dto);

	    if (row > 0) {
	        if ((files != null && files.length > 0) || (keepFileIdx != null && !keepFileIdx.isEmpty())) {
	            // 기존 파일 리스트 조회 및 삭제 (keepFileIdx를 기준으로 삭제)
	            List<Map<String, String>> currentFiles = dao.fileList(dto.getPost_idx());

	            for (Map<String, String> file : currentFiles) {
	            	String fileIdx = String.valueOf(file.get("file_idx"));
	                if (keepFileIdx == null || !keepFileIdx.contains(fileIdx)) {
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
	        java.nio.file.Path path = java.nio.file.Paths.get(uploadDir, savedName);
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
	    int offset = (page - 1) * post_count; // 페이징 처리하지기

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
	    
	    if (currentType == null) {
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
