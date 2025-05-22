package com.withcare.comment.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.comment.dao.ComDAO;
import com.withcare.comment.dto.ComDTO;
import com.withcare.comment.dto.MenDTO;
import com.withcare.noti.service.NotiService;

@Service
public class ComService {

    Logger log = LoggerFactory.getLogger(getClass());

    Map<String, Object> result = null;

    @Autowired
    ComDAO dao;

    @Autowired
    NotiService notiService;

    // WRITE COMMENT

    public Map<String, Object> writeCom(ComDTO dto) {
        result = new HashMap<>();

        // 댓글 작성
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

            // 댓글 내용에서 멘션된 아이디 조회
            String content = dto.getCom_content();
            Set<String> mentionId = getId(content); // 아이디 갖고 올거에요

            // 멘션 정보 저장할 것
            for (String menId : mentionId) {
                MenDTO menDto = new MenDTO();
                menDto.setCom_idx(dto.getCom_idx()); // 어떤 댓글에서 멘션 했는지
                menDto.setMen_id(menId); // 멘션 당한 사람 아이디
                menDto.setMen_writer_id(dto.getId()); // 멘션한 사람 아이디
                menDto.setMen_content(content); // 원본 댓글 내용
                menDto.setMen_blind_yn(false); // 블라인드 여부

                dao.writeMention(menDto); // DB에 저장

                // 멘션 알림 저장 - 본인을 멘션한 경우는 알림 제외
                if (!menId.equals(dto.getId())) {
                    notiService.sendMentionNoti(dto.getCom_idx(), dto.getPost_idx(), dto.getId(), menId);
                }
            }

            result.put("success", true);
            result.put("idx", dto.getCom_idx());
            result.put("mentioned", mentionId);
        } else if (row == 0) {
            result.put("success", false);
            result.put("mentioned", new HashSet<String>());
        }

        return result;
    }

    // GET MENTIONED ID
    private Set<String> getId(String content) {
        List<String> memberIdList = dao.selectId(); // DB에서 멘션된 아이디 가져오기
        Set<String> mentioned = new HashSet<>(); // 멘션된 아이디 저장할 set

        for (String memberId : memberIdList) {
            if (content.contains("@" + memberId)) { // @ 뒤에 아이디 오는지 확인
                mentioned.add(memberId); // 존재하는 아이디면 set에 추가
            }
        }

        return mentioned; // 멘션된 아이디 반환
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
