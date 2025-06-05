package com.withcare.profile.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.withcare.comment.dto.ComDTO;
import com.withcare.comment.dto.MenDTO;
import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;
import com.withcare.profile.dto.LevelDTO;
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

	int getUserPostCount(String id);

	int getUserCommentCount(String id);

	int getUserLikeCount(String id);

	int getUserTimelineCount(String id);

    int getUserCurrentLevel(String userId);

	Integer getUserLvIdx(String id);

	// ProfileDAO.java에 추가할 메서드들 (기존 DTO 활용)

	// HashMap으로 배지 정보 반환 (기존 DTO 조합용)
	List<Map<String, Object>> getPublicUserBadges(String userId);
}
