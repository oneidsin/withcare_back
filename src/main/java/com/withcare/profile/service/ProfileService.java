package com.withcare.profile.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.withcare.comment.dto.ComDTO;
import com.withcare.comment.dto.MenDTO;
import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;
import com.withcare.profile.dao.ProfileDAO;
import com.withcare.profile.dto.ProfileDTO;
import com.withcare.search.dto.SearchDTO;

@Service
public class ProfileService {

	@Autowired ProfileDAO dao;
	
    @Value("${file.upload-dir}")
    private String uploadDir;
	
	Logger log = LoggerFactory.getLogger(getClass());

	/*
	 * public boolean saveProfile(Map<String, Object> map) { return
	 * dao.saveProfile(map) > 0; }
	 */

	// 프로필 보기
	 public ProfileDTO getProfile(String id) {
	        return dao.getProfileById(id);
	}

	 // 프로필 수정
	 public int updateProfile(ProfileDTO dto) {
	        return dao.updateProfile(dto);
}

	 // 타인 프로필 보기
	public ProfileDTO getProfileById(String id) {
		return dao.getProfileById(id);
	}

	public List<PostDTO> getUserPosts(String id) {
		return dao.getUserPosts(id);
	}

	public List<ComDTO> getUserComments(String id) {
		return dao.getUserComments(id);
	}

	public List<LikeDislikeDTO> getUserLikes(String id) {
		return dao.getUserLikes(id);
	}

	public List<SearchDTO> getUserSearches(String id) {
		return dao.getUserSearches(id);
	}

	public List<MenDTO> getUserMentions(String id) {
		return dao.getUserMentions(id);
	}

	public String saveProfileImage(MultipartFile file) {
		try {
			String original = file.getOriginalFilename();
			if (original == null || !original.matches(".*\\.(png|jpg|jpeg|webp)$")) {
				throw new IllegalArgumentException("지원하지 않는 이미지 형식입니다.");
			}
			
			String ext = original.substring(original.lastIndexOf("."));
			String fileName = UUID.randomUUID() + ext;
			
			// profile 폴더에 저장
			Path saveDir = Paths.get(uploadDir, "profile");
			Files.createDirectories(saveDir);
			Path savePath = saveDir.resolve(fileName);
			Files.write(savePath, file.getBytes());
			
			return "profile/" + fileName;
		} catch (Exception e) {
			log.error("프로필 이미지 저장 실패", e);
			throw new RuntimeException("프로필 이미지 저장에 실패했습니다.", e);
		}
	}
	// 카운트 메서드들 추가
    public int getUserPostCount(String id) {
        return dao.getUserPostCount(id);
    }
    
    public int getUserCommentCount(String id) {
        return dao.getUserCommentCount(id);
    }
    
    public int getUserLikeCount(String id) {
        return dao.getUserLikeCount(id);
    }
    
    public int getUserTimelineCount(String id) {
        return dao.getUserTimelineCount(id);
    }
	
}
