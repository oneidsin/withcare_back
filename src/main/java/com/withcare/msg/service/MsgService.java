package com.withcare.msg.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.msg.dao.MsgDAO;
import com.withcare.msg.dto.MsgDTO;
import com.withcare.noti.service.NotiService;

@Service
public class MsgService {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired MsgDAO dao;

    @Autowired NotiService notiService;

    // Send MSG
    public void sendMsg(MsgDTO dto) {
        dto.setMsg_sent_at(new Timestamp(System.currentTimeMillis()));
        dto.setMsg_read(false);
        dto.setSender_msg_status("N");// N : 초기 상태 S : 보관 D : 삭제
        dto.setReceiver_msg_status("N");

        dao.sendMsg(dto);

        // 차단 여부 확인 후 알림 저장
        if (!dao.isBlocked(dto.getReceiver_id(), dto.getSender_id())) {
            // 쪽지 알림 저장 - 생성된 msg_idx 사용
            notiService.sendMessageNoti(dto.getMsg_idx(), dto.getSender_id(), dto.getReceiver_id());
        }
    }
    
    private int msgCnt = 15; // OUT BOX 한 페이지의 쪽지 개수

    // OUTBOX
    public Map<String, Object> outbox(String id, int page) {
    	Map<String, Object> result = new HashMap<>();
    	Map<String, Object> param = new HashMap<>();
    	
    	int offset = (page - 1) * msgCnt;
    	
    	param.put("sender_id", id);
        param.put("offset", offset);
        param.put("msgCnt", msgCnt);

        result.put("page", page);
        result.put("list", dao.outbox(param));
        result.put("pages", dao.pages(param));
        
    	return result;
    }

    // INBOX
    public List<MsgDTO> inbox(String id, int page, int size) {
    	int offset = page * size;
    	Map<String, Object> param = new HashMap<>();
    	
    	param.put("receiver_id", id);
    	param.put("size", size);
    	param.put("offset", offset);
        param.put("status", "N");  // 받은 쪽지함은 status가 'N'인 것만
        return dao.inbox(param);
    }
    
    // 전체 메세지 수 조회 
 	public int getInboxCnt(String id) {
        Map<String, Object> param = new HashMap<>();
        param.put("receiverId", id);
        param.put("status", "N");  // 받은 쪽지함은 status가 'N'인 것만
 		return dao.getInboxCnt(param);
 	}

    // 보관된 쪽지함
    public List<MsgDTO> savedInbox(String id, int page, int size) {
        int offset = page * size;
        Map<String, Object> param = new HashMap<>();
        
        param.put("receiver_id", id);
        param.put("size", size);
        param.put("offset", offset);
        param.put("status", "S");  // 보관된 쪽지함은 status가 'S'인 것만
        return dao.inbox(param);
    }

    // 보관된 쪽지 수 조회
    public int getSavedInboxCnt(String id) {
        Map<String, Object> param = new HashMap<>();
        param.put("receiverId", id);
        param.put("status", "S");  // 보관된 쪽지함은 status가 'S'인 것만
        return dao.getInboxCnt(param);
 	}

    // MSG DETAIL
    public MsgDTO msgDetail(int idx) {
        return dao.msgDetail(idx);
    }

    // DELETE MSG (받은 쪽지 기준)
    public void msgDel(int idx) {
        dao.msgDel(idx);
    }

    // DELETE MSG (보낸 쪽지 기준)
    public void msgDelOut(int idx) {
        dao.msgDelOut(idx);
    }

    // SAVE MSG (받은 쪽지 기준)
    public void msgSave(int idx) {
        dao.msgSave(idx);
    }

    // SAVE MSG (보낸 쪽지 기준)
    public void msgSaveOut(int idx) {
        dao.msgSaveOut(idx);
    }

    // 수신자가 읽었을 때 읽음 처리
    public void readYN(int msg_idx) {
        dao.readYN(msg_idx);
    }

    

}
