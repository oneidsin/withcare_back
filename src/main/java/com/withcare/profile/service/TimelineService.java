package com.withcare.profile.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.withcare.profile.dao.TimelineDAO;
import com.withcare.profile.dto.TimelineDTO;

@Service
public class TimelineService {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired TimelineDAO dao;

    // 타임라인 작성
    public void writeTimeline(TimelineDTO dto) {
        dao.writeTimeline(dto);
    }

    // 타임라인 수정
    public void update_timeline(TimelineDTO dto) {
        dao.update_timeline(dto);
    }

    // 타임라인 삭제
    public boolean del_timeline(int time_idx, String id) {
    	
        String ownerId = dao.getTimelineOwnerId(time_idx);

        if (ownerId == null) {
            log.warn("삭제 대상 타임라인 없음, time_idx={}", time_idx);
            return false;
        }

        if (!ownerId.equals(id)) {
            log.warn("삭제 권한 없음, 요청자: {}, 작성자: {}", id, ownerId);
            return false;
        }

        dao.del_timeline(time_idx);
        return true;
    }
}
