package com.withcare.profile.service;

import com.withcare.profile.dao.BadgeDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BadgeService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    BadgeDAO dao;

    public List<Map<String, Object>> getMemberBadgeList(String id) {
        return dao.getMemberBadgeList(id);
    }

    public List<Map<String, Object>> getMemberAcquiredBadge(String id) {
        return dao.getMemberAcquiredBadge(id);
    }

    // 배지 획득 로직(중복 체크)
    public Map<String, Object> acquiredBadge(String id, int bdg_idx) {
        Map<String, Object> result = new HashMap<>();

        // 이미 획득한 배지인지 확인(획득한 배지인지를 조회해서 row 가 1 이상이면 이미 획득했다는 것)
        boolean already_acquired = (dao.isBadgeAcquired(id, bdg_idx) > 0);

        // 이미 획득한 배지면 획득 실패 처리
        if (already_acquired) {
            log.info("유저 {}는 이미 배지 idx {}를 획득했습니다", id, bdg_idx);
            result.put("msg", "이미 획득한 배지입니다.");
            result.put("success", false);
            return result;
        }

        // 새로운 배지 획득 처리
        log.info("유저 {}가 이미 새로운 배지 {}를 획득했습니다", id, bdg_idx);
        dao.insertAcquiredBadge(id, bdg_idx);
        result.put("msg", "새로운 배지를 획득했습니다.");
        result.put("success", true);
        return result;
    }

    // 대표 배지 설정
    public Map<String, Object> updateBadgeSym(String id, int bdg_idx) {
        Map<String, Object> result = new HashMap<>();

        // 1. 유저가 가진 모든 대표 배지 설정 초기화
        dao.clearAllBadgeSym(id);

        // 2. 대표 배지 설정(updated_row 가 1 이상이면 성공)
        int updated_row = dao.updateBadgeSym(id, bdg_idx);

        if (updated_row > 0) {
            result.put("msg", "대표 배지 설정 완료");
            result.put("success", true);
        } else {
            result.put("msg", "대표 배지 설정 실패");
            result.put("success", false);
        }
        return result;
    }
}
