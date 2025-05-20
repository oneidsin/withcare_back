package com.withcare.profile.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    // 유저의 타임라인 리스트
    public Map<String, List<TimelineDTO>> timeline_list(String id) {
        List<TimelineDTO> list = dao.timeline_list(id);
        Map<String, List<TimelineDTO>> result = new TreeMap<>(Collections.reverseOrder());

        for (TimelineDTO dto : list) {
            Date day = dto.getDay();
            Calendar cal = Calendar.getInstance();
            cal.setTime(day);
            int yearInt = cal.get(Calendar.YEAR);
            String year = String.valueOf(yearInt);
            result.computeIfAbsent(year, k -> new ArrayList<>()).add(dto);
        }


        return result;
    }

	public List<TimelineDTO> public_list() {
		return dao.public_list();
	}

}
