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

	List<PostDTO> postList(int offset, int post_count, int board_idx, String sort, String searchType, String keyword);
	
	int postPages(int board_idx, String searchType, String keyword);

	List<Integer> LikeType(String id, int post_idx);

	int likeDelete(String id, int post_idx);

	int likeInsert(LikeDislikeDTO dto);

	int likeUpdate(LikeDislikeDTO dto);

	int likeCnt(int post_idx);

	int dislikeCnt(int post_idx);

	String postWriter(int post_idx); // 게시글의 작성자 ID 가져오기

	Integer userLevel(String userId); // 사용자의 level_idx 가져오기, null 확인을 위해 Integer 반환

	int fileInsert(Map<String, Object> param);

	int fileDelete(String file_idx); // 기본 키 file_idx로 파일 삭제

	List<Map<String, String>> fileList(int post_idx);

	Map<String, String> fileInfo(String file_idx); // 단일 파일 정보 가져오기

	PostDTO postIdx(int post_idx); // post_idx로 전체 게시글 상세 정보 가져오기 

	int fileDeleteUrl(String savedName); // file_url로 파일 레코드 삭제

	int getBoardIdx(int postIdx);
}
