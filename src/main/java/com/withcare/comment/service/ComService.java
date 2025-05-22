package com.withcare.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.withcare.noti.service.NotiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.comment.dao.ComDAO;
import com.withcare.comment.dto.ComDTO;

@Service
public class ComService {

    Logger log = LoggerFactory.getLogger(getClass());

    Map<String, Object> result = null;

    @Autowired
    ComDAO dao;

    @Autowired
    private NotiService notiService;

    // WRITE COMMENT
    public boolean writeCom(ComDTO dto) {

        int row = dao.writeCom(dto);
        if (row > 0) {
            // 댓글 작성 성공 시 알림 저장
            int com_idx = dto.getCom_idx();
            int post_idx = dto.getPost_idx();
            String writer_id = dto.getId();
            String post_writer_id = dao.getPostWriterId(dto.getPost_idx());
            if (!writer_id.equals(post_writer_id)) { // 본인 댓글은 알림 제외
                notiService.sendCommentNoti(com_idx, post_idx, writer_id, post_writer_id);
            }
            return true;
        }

        return false;
    }

    // UPDATE COMMENT
    public boolean updateCom(ComDTO dto, String id) {

        String writerId = dao.writerId(dto.getCom_idx());

        if (!writerId.equals(id)) {
            int lv_idx = dao.userLevel(id);
            if (lv_idx != 7) {
                return false;
            }
        }

        int row = dao.updateCom(dto);
        return row > 0;
    }

    // DELETE COMMENT
    public boolean delCom(ComDTO dto, String id) {

        String writerId = dao.writerId(dto.getCom_idx());
        if (id == null) {
            return false;
        }

        if (!writerId.equals(id)) {
            int lv_idx = dao.userLevel(id);
            if (lv_idx != 7) {
                return false;
            }
        }

        int row = dao.delCom(dto);
        return row > 0;
    }

    // COMMENT LIST
    public Map<String, Object> comList(int post_idx) {

        result = new HashMap<>();

        List<ComDTO> comList = dao.comList(post_idx);
        int comments = dao.comCnt(post_idx);

        result.put("list", comList);
        result.put("comments", comments);

        return result;
    }

}
