package com.withcare.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.admin.dto.AdminMemberDTO;
import com.withcare.admin.dto.AdminMemberDetailDTO;
import com.withcare.comment.dto.ComDTO;
import com.withcare.comment.dto.MenDTO;
import com.withcare.post.dto.LikeDislikeDTO;
import com.withcare.post.dto.PostDTO;
import com.withcare.profile.dto.BadgeDTO;
import com.withcare.profile.dto.LevelDTO;
import com.withcare.profile.dto.TimelineDTO;

@Mapper
public interface AdminDAO {

	int userLevel(String loginId);
	
	int levelUpdate(Map<String, Object> params);

	List<AdminMemberDTO> adminMemberList(Map<String, Object> params);

	AdminMemberDetailDTO adminMemberDetail(String targetId);

	List<PostDTO> adminMemberPost(String targetId);

	List<ComDTO> adminMemberCom(String targetId);

	List<MenDTO> adminMemberMen(String targetId);

	List<LikeDislikeDTO> adminMemberLike(String targetId);

	List<TimelineDTO> adminMemberTimeline(String targetId);

	int adminBdgAdd(BadgeDTO bdg);

	int adminBdgUpdate(BadgeDTO bdg);

	int adminBdgDelete(BadgeDTO bdg);

	int adminLevelAdd(LevelDTO level);


}
