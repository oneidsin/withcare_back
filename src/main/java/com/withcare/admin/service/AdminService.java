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
		
		List<ComDTO> comment = dao.adminMemberCom(targetId);
		List<MenDTO> mention = dao.adminMemberMen(targetId);
		
		List<Map<String, Object>> unifiedList = new ArrayList<Map<String,Object>>();
		
		for (ComDTO com : comment) {
			Map<String, Object> item = new HashMap<>();
			item.put("type", "comment");
			item.put("id", com.getCom_idx());
			item.put("writerId", com.getId());
			item.put("content", com.getCom_content());
			item.put("createDate", com.getCom_create_date());
			item.put("updateDate", com.getCom_update_date());
			item.put("blindYn", com.isCom_blind_yn());
			unifiedList.add(item);
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
		
		// 최신 기준: create_date와 update_date 중 더 나중 날짜로 정렬
		unifiedList.sort((a, b) -> {
			Timestamp aCreate = (Timestamp) a.get("createDate");
		    Timestamp aUpdate = (Timestamp) a.get("updateDate");
		    Timestamp bCreate = (Timestamp) b.get("createDate");
		    Timestamp bUpdate = (Timestamp) b.get("updateDate");
		    
		    Timestamp aLatest = (aUpdate != null && aUpdate.after(aCreate)) ? aUpdate : aCreate;
		    Timestamp bLatest = (bUpdate != null && bUpdate.after(bCreate)) ? bUpdate : bCreate;

		    return bLatest.compareTo(aLatest);  // 최신순 정렬
		});

		return unifiedList;
		
	}


	public List<LikeDislikeDTO> adminMemberLike(String targetId) {
		return dao.adminMemberLike(targetId);
	}


	public List<TimelineDTO> adminMemberTimeline(String targetId) {
		return dao.adminMemberTimeline(targetId);
	}

}
