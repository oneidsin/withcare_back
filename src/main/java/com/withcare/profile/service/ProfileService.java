package com.withcare.profile.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
		return dao.saveProfileImage(file);
	}
	
}
