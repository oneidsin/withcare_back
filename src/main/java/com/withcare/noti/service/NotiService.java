package com.withcare.noti.service;

import com.withcare.noti.dao.NotiDAO;
import com.withcare.noti.dto.NotiDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotiService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    NotiDAO dao;

    private static final int LIMIT = 100; // 한 번에 가져올 알림 개수

    public List<Map<String, Object>> getNoti(String id, int offset) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("limit", LIMIT);
        params.put("offset", offset);
        return dao.getNoti(params);
    }

    // 댓글 알림 저장
    public void sendCommentNoti(int com_idx, int post_idx, String sender_id, String receiver_id) {
        NotiDTO notiDTO = new NotiDTO();
        notiDTO.setRelate_item_id(com_idx);
        notiDTO.setPost_idx(post_idx);
        notiDTO.setNoti_sender_id(sender_id);
        notiDTO.setRelate_user_id(receiver_id);
        notiDTO.setNoti_type("comment");
        notiDTO.setContent_pre(sender_id + " 님이 회원님의 게시글에 댓글을 남겼습니다.");
        dao.insertNoti(notiDTO);
    }

    // 멘션 알림 저장
    public void sendMentionNoti(int com_idx, int post_idx, String sender_id, String receiver_id) {
        NotiDTO notiDTO = new NotiDTO();
        notiDTO.setRelate_item_id(com_idx);
        notiDTO.setPost_idx(post_idx);
        notiDTO.setNoti_sender_id(sender_id);
        notiDTO.setRelate_user_id(receiver_id);
        notiDTO.setNoti_type("mention");
        notiDTO.setContent_pre(sender_id + " 님이 회원님을 멘션했습니다.");
        dao.insertNoti(notiDTO);
    }

    // 알림 삭제(1개)
    public boolean deleteNoti(String id, int noti_idx) {
        int row = dao.deleteNoti(id, noti_idx);
        return row > 0;
    }

    // 알림 전체 삭제
    public boolean deleteAllNoti(String id) {
        int row = dao.deleteAllNoti(id);
        return row > 0;
    }

    // 알림 읽음 확인
    public boolean readNoti(String id, int noti_idx) {
        int row = dao.readNoti(id, noti_idx);
        return row > 0;
    }

}
