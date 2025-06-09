package com.withcare.admin.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.admin.dao.AdminDAO;
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

@Service
public class AdminService {

	@Autowired AdminDAO dao;
	
	public int userLevel(String loginId) {
		return dao.userLevel(loginId);
	}

	
	public boolean levelUpdate(Map<String, Object> params) {
		int row = dao.levelUpdate(params);
		return row>0;
	}


	public List<AdminMemberDTO> adminMemberList(Map<String, Object> params) {
		return dao.adminMemberList(params);
	}


	public AdminMemberDetailDTO adminMemberDetail(String targetId) {
		return dao.adminMemberDetail(targetId);
	}


	public List<PostDTO> adminMemberPost(String targetId) {
		return dao.adminMemberPost(targetId);
	}

	public List<Map<String, Object>> adminMemberComments(String targetId) {
		// 특정 사용자가 작성한 댓글 목록 조회
		List<ComDTO> comment = dao.adminMemberCom(targetId);
		// 특정 사용자가 작성한 멘션 목록 조회
		List<MenDTO> mention = dao.adminMemberMen(targetId);
		
		// 댓글 멘션 통합할 리스트 생성 (리턴 결과물)
		List<Map<String, Object>> unifiedList = new ArrayList<Map<String,Object>>();
		
		// 댓글 리스트 MAP 형태로 변환해 통합 리스트에 추가
		for (ComDTO com : comment) {
			Map<String, Object> item = new HashMap<>();
			item.put("type", "comment"); // 타입 구분
			item.put("com_idx", com.getCom_idx()); // 댓글 고유 ID (프론트엔드 필드명에 맞춤)
			item.put("id", com.getId()); // 작성자 ID
			item.put("com_content", com.getCom_content()); // 댓글 내용 (프론트엔드 필드명에 맞춤)
			item.put("com_create_date", com.getCom_create_date()); // 작성 일자 (프론트엔드 필드명에 맞춤)
			item.put("com_update_date", com.getCom_update_date()); // 수정 일자 (프론트엔드 필드명에 맞춤)
			item.put("com_blind_yn", com.isCom_blind_yn()); // 블라인드 여부
			
			// 게시글 정보 추가 - 댓글이 속한 게시글의 제목과 ID
			try {
				// 댓글이 속한 게시글 정보 조회 (DAO에 메서드 추가 필요)
				Map<String, Object> postInfo = dao.getPostInfoByCommentId(com.getCom_idx());
				if (postInfo != null) {
					item.put("post_title", postInfo.get("post_title")); // 게시글 제목
					item.put("post_idx", postInfo.get("post_idx")); // 게시글 ID
				} else {
					item.put("post_title", "삭제된 게시글");
					item.put("post_idx", null);
				}
			} catch (Exception e) {
				item.put("post_title", "게시글 정보 없음");
				item.put("post_idx", null);
			}
			
			unifiedList.add(item); // 리스트에 추가
		}
		
		for (MenDTO men : mention) {
			Map<String, Object> item = new HashMap<>();
			item.put("type", "mention");
			item.put("com_idx", men.getMen_idx()); // 프론트엔드 필드명에 맞춤
			item.put("id", men.getMen_writer_id());
			item.put("com_content", men.getMen_content()); // 프론트엔드 필드명에 맞춤
			item.put("com_create_date", men.getMen_create_date()); // 프론트엔드 필드명에 맞춤
			item.put("com_update_date", men.getMen_update_date()); // 프론트엔드 필드명에 맞춤
			item.put("com_blind_yn", men.isMen_blind_yn());
			
			// 멘션이 속한 게시글 정보 추가
			try {
				// 멘션이 속한 게시글 정보 조회 (DAO에 메서드 추가 필요)
				Map<String, Object> postInfo = dao.getPostInfoByMentionId(men.getMen_idx());
				if (postInfo != null) {
					item.put("post_title", postInfo.get("post_title")); // 게시글 제목
					item.put("post_idx", postInfo.get("post_idx")); // 게시글 ID
				} else {
					item.put("post_title", "삭제된 게시글");
					item.put("post_idx", null);
				}
			} catch (Exception e) {
				item.put("post_title", "게시글 정보 없음");
				item.put("post_idx", null);
			}
			
			unifiedList.add(item);
		}
		
		// create_date와 update_date 중 더 나중 날짜를 기준으로 최신순 정렬
		unifiedList.sort((a, b) -> {
			Timestamp aCreate = (Timestamp) a.get("com_create_date"); // 필드명 변경
		    Timestamp aUpdate = (Timestamp) a.get("com_update_date"); // 필드명 변경
		    Timestamp bCreate = (Timestamp) b.get("com_create_date"); // 필드명 변경
		    Timestamp bUpdate = (Timestamp) b.get("com_update_date"); // 필드명 변경
		    
		    // 각각 최신 시간 계산
		    Timestamp aLatest = (aUpdate != null && aUpdate.after(aCreate)) ? aUpdate : aCreate;
		    Timestamp bLatest = (bUpdate != null && bUpdate.after(bCreate)) ? bUpdate : bCreate;

		    return bLatest.compareTo(aLatest);  // 최신순 정렬
		});

		// 정렬된 통합 리스트 반환
		return unifiedList;
		
	}


	public List<LikeDislikeDTO> adminMemberLike(String targetId) {
		return dao.adminMemberLike(targetId);
	}


	public List<TimelineDTO> adminMemberTimeline(String targetId) {
		return dao.adminMemberTimeline(targetId);
	}

	public List<BadgeDTO> adminBdgList() {
		return dao.adminBdgList();
	}
	
	public boolean adminBdgAdd(BadgeDTO bdg) {
		int row = dao.adminBdgAdd(bdg);
		return row>0;
	}


	public boolean adminBdgUpdate(BadgeDTO bdg) {
		int row = dao.adminBdgUpdate(bdg);
		return row>0;
	}


	public boolean adminBdgDelete(BadgeDTO bdg) {
		int row = dao.adminBdgDelete(bdg);
		return row>0;
	}


	public boolean adminLevelAdd(LevelDTO level) {
		int row = dao.adminLevelAdd(level);
		return row>0;
	}


	public boolean adminLevelUpdate(LevelDTO level) {
		int row = dao.adminLevelUpdate(level);
		return row>0;
	}


	public boolean adminLevelDelete(int lv_idx) {
		int row = dao.adminLevelDelete(lv_idx);
		return row>0;
	}


	public int adminLevelCnt(int lvIdx) {
		return dao.adminLevelCnt(lvIdx);
	}


	public int adminMemberCnt(Map<String, Object> result) {
		return dao.adminMemberCnt(result);
	}


	public List<LevelDTO> levelList() {
		return dao.levelList();
	}

	public LevelDTO getLevelById(int lvIdx) {
		return dao.getLevelById(lvIdx);
	}

	// ===== 암 종류 관리 =====
	
	public List<CancerDTO> adminCancerList() {
		return dao.adminCancerList();
	}
	
	public boolean adminCancerAdd(CancerDTO cancer) {
		int row = dao.adminCancerAdd(cancer);
		return row > 0;
	}
	
	public boolean adminCancerUpdate(CancerDTO cancer) {
		int row = dao.adminCancerUpdate(cancer);
		return row > 0;
	}
	
	public boolean adminCancerDelete(int cancerIdx) {
		int row = dao.adminCancerDelete(cancerIdx);
		return row > 0;
	}
	
	public int adminCancerUserCnt(int cancerIdx) {
		return dao.adminCancerUserCnt(cancerIdx);
	}

	// ===== 병기 관리 =====
	
	public List<StageDTO> adminStageList() {
		return dao.adminStageList();
	}
	
	public boolean adminStageAdd(StageDTO stage) {
		int row = dao.adminStageAdd(stage);
		return row > 0;
	}
	
	public boolean adminStageUpdate(StageDTO stage) {
		int row = dao.adminStageUpdate(stage);
		return row > 0;
	}
	
	public boolean adminStageDelete(int stageIdx) {
		int row = dao.adminStageDelete(stageIdx);
		return row > 0;
	}
	
	public int adminStageUserCnt(int stageIdx) {
		return dao.adminStageUserCnt(stageIdx);
	}

}
