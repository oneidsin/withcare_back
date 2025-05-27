package com.withcare.profile.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.withcare.comment.dto.ComDTO;
import com.withcare.comment.dto.MenDTO;
import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;
import com.withcare.profile.dto.ProfileDTO;
import com.withcare.search.dto.SearchDTO;

@Mapper
public interface ProfileDAO {

	int saveProfile(Map<String, Object> map);

	ProfileDTO getProfileById(String id);

	int updateProfile(ProfileDTO dto);

	int increaseAccessCount(String id);

	void insertProfile(String id);

	List<PostDTO> getUserPosts(String id);

	List<ComDTO> getUserComments(String id);

	List<LikeDislikeDTO> getUserLikes(String id);

	List<SearchDTO> getUserSearches(String id);

	List<MenDTO> getUserMentions(String id);

	String saveProfileImage(MultipartFile file);
}
