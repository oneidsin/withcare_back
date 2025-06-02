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
import com.withcare.profile.dto.LevelDTO;
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
			item.put("id", com.getCom_idx()); // 댓글 고유 ID
			item.put("writerId", com.getId()); // 작성자 ID
			item.put("content", com.getCom_content()); // 댓글 내용
			item.put("createDate", com.getCom_create_date()); // 작성 일자
			item.put("updateDate", com.getCom_update_date()); // 수정 일자
			item.put("blindYn", com.isCom_blind_yn()); // 블라인드 여부
			unifiedList.add(item); // 리스트에 추가
		}
		
		for (MenDTO men : mention) {
			Map<String, Object> item = new HashMap<>();
			item.put("type", "mention");
			item.put("id", men.getMen_idx());
			item.put("writerId", men.getMen_writer_id());
			item.put("content", men.getMen_content());
			item.put("createDate", men.getMen_create_date());
			item.put("udateDate", men.getMen_update_date());
			item.put("blindYn", men.isMen_blind_yn());
			unifiedList.add(item);
		}
		
		// create_date와 update_date 중 더 나중 날짜를 기준으로 최신순 정렬
		unifiedList.sort((a, b) -> {
			Timestamp aCreate = (Timestamp) a.get("createDate");
		    Timestamp aUpdate = (Timestamp) a.get("updateDate");
		    Timestamp bCreate = (Timestamp) b.get("createDate");
		    Timestamp bUpdate = (Timestamp) b.get("updateDate");
		    
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


}
