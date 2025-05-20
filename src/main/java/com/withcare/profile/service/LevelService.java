package com.withcare.profile.service;

import com.withcare.profile.dao.LevelDAO;
import com.withcare.profile.dto.LevelDTO;
import com.withcare.profile.dto.MemberActivityDTO;
import com.withcare.profile.dto.MemberLevelDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LevelService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    LevelDAO dao;

    public MemberLevelDTO getMemberLevel(String id) {
        return dao.getMemberLevel(id);
    }

    public MemberActivityDTO getMemberActivity(String id) {
        return dao.getMemberActivity(id);
    }

    // 레벨 조건 확인 및 레벨업 처리
    public Map<String, Object> calculateAndUpdateLevel(String id) {
        Map<String, Object> result = new HashMap<>();
        // 1. 사용자의 활동 내역 가져오기
        MemberActivityDTO stats = dao.getMemberActivity(id);
        log.info("stats : {}", stats);

        // 1.1 활동 내역(글 수, 댓글 수, 추천받은 수, 타임라인 수, 방문 수)
        int post_count = stats.getPost_count();
        int comment_count = stats.getComment_count();
        int like_count = stats.getLike_count();
        int timeline_count = stats.getTimeline_count();
        int access_count = stats.getAccess_count();

        // 2. 현재 사용자의 레벨 정보
        MemberLevelDTO current_level = dao.getMemberLevel(id);
        log.info("current_level : {}", current_level.getLv_no());
        int current_lv_no = current_level.getLv_no();

        // 2.1 관리자 레벨은 제외
        if (current_lv_no == 0) {
            result.put("success", false);
            result.put("msg", "관리자이므로 레벨업 불가");
            return result;
        }

        // 3. 전체 레벨 목록
        List<LevelDTO> levels = dao.getAllLevels();
        log.info("levels : {}", levels);

        // 3.1 최고 레벨 설정(for 문 돌면서 가장 큰 lv_no 를 max_lv_no 에 저장)
        int max_lv_no = -1;
        for (LevelDTO level : levels) {
            if (level.getLv_no() > max_lv_no) {
                max_lv_no = level.getLv_no();
            }
        }

        // 3.2 최고 레벨이면 레벨업 불가능
        if (current_lv_no == max_lv_no) {
            result.put("success", false);
            result.put("msg", "이미 최고 레벨입니다.");
            return result;
        }

        int target_lv_idx = -1;
        int target_lv_no = current_lv_no;

        // 조건을 만족하는 가장 높은 레벨 찾기
        for (LevelDTO level : levels) {
            int lv_no = level.getLv_no();

            // 현재보나 낮거나 같은 lv_no 는 패스. 관리자도 패스
            if (lv_no <= current_lv_no || lv_no == 0) {
                continue;
            }

            if (post_count >= level.getPost_cnt()
                    && comment_count >= level.getCom_cnt()
                    && like_count >= level.getLike_cnt()
                    && timeline_count >= level.getTime_cnt()
                    && access_count >= level.getAccess_cnt()) {
                target_lv_idx = level.getLv_idx();
                target_lv_no = lv_no;
            }
        }

        // 레벨업
        if (target_lv_no > current_lv_no) {
            int updated = dao.updateMemberLevel(id, target_lv_idx);
            result.put("success", updated > 0); // updated row 가 1 이상이면 true
            result.put("new_lv_idx", target_lv_idx);
            result.put("new_lv_no", target_lv_no);
            result.put("msg", "레벨이 상승했습니다.");
        } else {
            result.put("success", false);
            result.put("msg", "레벨업 조건을 만족하지 못했습니다.");
        }

        return result;
    }
}
