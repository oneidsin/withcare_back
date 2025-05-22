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

    public List<NotiDTO> getNoti(Map<String, String> params) {
        List<NotiDTO> noti_list = dao.getNoti(params.get("id"));

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
    public boolean sendCommentNoti(int com_idx) {
        Map<String, String> info = dao.getNotiInfoFromComment(com_idx);

        String sender_id = info.get("sender_id");
        String receiver_id = info.get("receiver_id");

        NotiDTO noti = new NotiDTO();
        noti.setNoti_sender_id(sender_id);
        noti.setRelate_user_id(receiver_id);
        noti.setRelate_item_id(com_idx);
        noti.setNoti_type("comment");

        int row = dao.insertNoti(noti);
        return row > 0;
    }
}
