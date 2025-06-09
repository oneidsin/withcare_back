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
import com.withcare.profile.dto.CancerDTO;
import com.withcare.profile.dto.LevelDTO;
import com.withcare.profile.dto.StageDTO;
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

	int adminLevelUpdate(LevelDTO level);

	int adminLevelDelete(int lv_idx);

	int adminLevelCnt(int lvIdx);

	int adminMemberCnt(Map<String, Object> result);

	List<LevelDTO> levelList();

	LevelDTO getLevelById(int lvIdx);

	List<BadgeDTO> adminBdgList();

	// 댓글 ID로 해당 댓글이 속한 게시글 정보 조회
	Map<String, Object> getPostInfoByCommentId(int comIdx);
	
	// 멘션 ID로 해당 멘션이 속한 게시글 정보 조회
	Map<String, Object> getPostInfoByMentionId(int menIdx);

	// ===== 암 종류 관리 =====
	
	List<CancerDTO> adminCancerList();
	
	int adminCancerAdd(CancerDTO cancer);
	
	int adminCancerUpdate(CancerDTO cancer);
	
	int adminCancerDelete(int cancerIdx);
	
	int adminCancerUserCnt(int cancerIdx);

	// ===== 병기 관리 =====
	
	List<StageDTO> adminStageList();
	
	int adminStageAdd(StageDTO stage);
	
	int adminStageUpdate(StageDTO stage);
	
	int adminStageDelete(int stageIdx);
	
	int adminStageUserCnt(int stageIdx);

}
