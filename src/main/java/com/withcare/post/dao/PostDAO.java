package com.withcare.post.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;

@Mapper
public interface PostDAO {

	int postWrite(PostDTO dto);

	int postUpdate(PostDTO dto);

	int postDelete(PostDTO dto);

	PostDTO postDetail(int post_idx);

	int upHit(int post_idx);

	List<PostDTO> postList(int offset, int post_count, int board_idx);
	
	int postPages(int board_idx);

	Integer LikeType(String id, int post_idx);

	int likeDelete(String id, int post_idx);

	int likeInsert(LikeDislikeDTO dto);

	int likeUpdate(LikeDislikeDTO dto);

	int likeCnt(int post_idx);

	int dislikeCnt(int post_idx);

	String postWriter(int post_idx);

	int userLevel(String userId);

	int fileInsert(Map<String, Object> param);

	int fileIdx(String file_idx);

	int fileDelete(String file_idx);

	List<Map<String, String>> fileList(int post_idx);

	Map<String, String> fileInfo(String file_idx);
}
