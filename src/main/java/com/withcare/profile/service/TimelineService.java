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
    public void updateTimeline(TimelineDTO dto) {
        dao.updateTimeline(dto);
    }

    // 타임라인 삭제
    public boolean delTimeline(int time_idx, String id) {
    	
        String loginId = dao.get_token(time_idx);

        if (loginId == null) {
            log.warn("삭제 대상 타임라인 없음, time_idx={}", time_idx);
            return false;
        }

        if (!loginId.equals(id)) { // 로그인 id와 타임라인 작성자 id가 다르면 삭제
            log.warn("삭제 권한 없음, 요청자: {}, 작성자: {}", id, loginId);
            return false;
        }

        dao.delTimeline(time_idx);
        return true;
    }

    // 유저의 타임라인 리스트
    public Map<String, List<TimelineDTO>> timelineList(String id) {
        List<TimelineDTO> list = dao.timelineList(id); // DB에서 타임라인 목록 가져오기
        Map<String, List<TimelineDTO>> result = new TreeMap<>(Collections.reverseOrder()); // 연도별로 묶기 위해 Map 선언 , TreeMap = key 기준 자동 정렬되서 사용, reverse order 내림차순 하려고

        for (TimelineDTO dto : list) { // 모든 타임라인 항목 하나씩 돌며서 연도 추출 및 연도별로 그룹핑
            Date day = dto.getDay(); // 각 타임라인의 작성일자 꺼냄
            Calendar cal = Calendar.getInstance(); // 캘린더 객체 생성 후 날짜를해당 타임라인 날짜로 설정
            cal.setTime(day);
            int yearInt = cal.get(Calendar.YEAR); // 해당 날짜에서 연도 정보만 추출
            String year = String.valueOf(yearInt); // 연도 String으로 반환해서 Map의 key로 사용
            result.computeIfAbsent(year, k -> new ArrayList<>()).add(dto); // Map에서 해당 연도가 있는지 확인 해 있으면 기존 리스트 가져오고 없으면 새로운 리스트 생성해 key에 할당
        }


        return result;
    }

	public List<TimelineDTO> publicList(String id) {
		return dao.publicList(id);
	}

	

}
