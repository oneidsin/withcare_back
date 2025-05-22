package com.withcare.noti.service;

import com.withcare.noti.dao.NotiDAO;
import com.withcare.noti.dto.NotiDTO;
import com.withcare.noti.dto.NotiDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotiService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    NotiDAO dao;

    public List<NotiDTO> getNoti(String id) {
        List<NotiDTO> noti_list = dao.getNoti(id);

        for (NotiDTO noti : noti_list) {
            NotiDetailDTO detail = new NotiDetailDTO();
            // 디테일 DTO 에 알림 항목 id 에 알림 테이블에 알림 항목 id 를 가져와서 넣는다.
            detail.setRelated_item_id(noti.getRelate_item_id());

            // 알림 타입 판별
            String type = dao.detectItemType(noti.getRelate_item_id());
            detail.setRelated_item_type(type);

            // sender, receiver name
            String sender_name = dao.getMemberName(noti.getNoti_sender_id()); // 알림발송인 아이디
            String receiver_name = dao.getMemberName(noti.getRelate_user_id()); // 알림수신인 아이디
            detail.setSender_name(sender_name);
            detail.setReceiver_name(receiver_name);

            // content_pre 설정
            String msg = "";
            switch (type) {
                case "comment":
                    msg = sender_name + "님이 " + receiver_name + "님에게 댓글을 남겼습니다.";
                    break;
                case "mention":
                    msg = sender_name + "님이 " + receiver_name + "님을 멘션했습니다.";
                    break;
                case "message":
                    msg = sender_name + "님이 " + receiver_name + "님에게 쪽지를 보냈습니다.";
                    break;
                default:
                    msg = "알림이 도착했습니다.";
            }

            noti.setContent_pre(msg);
            detail.setContent_pre(msg);
            noti.setDetail(detail);
        }

        return noti_list;
    }

    // 댓글 알림 저장
    public void sendCommentNoti(int com_idx, int post_idx, String sender_id, String receiver_id) {
        NotiDTO notiDTO = new NotiDTO();
        notiDTO.setNoti_sender_id(sender_id);
        notiDTO.setRelate_user_id(receiver_id);
        notiDTO.setNoti_type("comment");
        notiDTO.setContent_pre(sender_id + " 님이 회원님의 게시글에 댓글을 남겼습니다.");
        dao.insertNoti(notiDTO);
    }


    public boolean deleteNoti(String id, int notiIdx) {
        int row = dao.deleteNoti(id, notiIdx);
        return row > 0;
    }
}
