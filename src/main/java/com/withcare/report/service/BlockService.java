package com.withcare.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.withcare.report.dto.BlockListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.withcare.report.dao.BlockDAO;

@Service
public class BlockService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    BlockDAO dao;

    // 쪽지 차단
    public boolean block(Map<String, Object> params) {
        String id = params.get("id").toString(); // 차단하는 사람 ID
        String blocked_id = params.get("blocked_id").toString(); // 차단될 사람 ID

        // 1. 차단 기록이 이미 있는지 확인(중복 체크)
        int existing_block_count = dao.checkIfBlocked(id, blocked_id);
        if (existing_block_count > 0) {
            return false;
        }

        // 2. 중복이 아니라면 차단 리스트에 등록
        int row = dao.block(params);
        // 3. 차단한 유저가 보낸 쪽지 자동 삭제 로직
        if (row > 0) {
            int success = dao.blockMsgDel(params.get("blocked_id").toString());
            log.info("차단한 유저가 보낸 쪽지 자동 삭제: {}", success);
        }
        return row > 0;
    }

    // 차단한 회원 리스트(유저)
    public Map<String, Object> blockList(Map<String, Object> params) {
        String id = (String) params.get("id");
        int page = (int) params.getOrDefault("page", 1);
        int pageSize = (int) params.getOrDefault("pageSize", 10);
        int offset = (page - 1) * pageSize;

        params.put("offset", offset);
        params.put("limit", pageSize);

        List<Map<String, Object>> list = dao.blockList(params);
        int totalCount = dao.blockListCount(id);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", totalCount);
        result.put("page", page);
        result.put("pageSize", pageSize);
        result.put("totalPage", (int) Math.ceil((double) totalCount / pageSize));

        return result;
    }


    // 차단 해제(유저)
    public boolean blockCancel(Map<String, Object> params) {
        int row = dao.blockCancel(params);
        return row > 0;
    }

    // 차단 관리(관리자)
    public Map<String, Object> getBlockList(Map<String, Object> params) {
        int page = Integer.parseInt(params.getOrDefault("page", 1).toString());
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", 10).toString());
        int offset = (page - 1) * pageSize;

        params.put("offset", offset);
        params.put("limit", pageSize);

        List<BlockListDTO> list = dao.getBlockList(params);
        int totalCount = dao.getBlockListCount(params);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", totalCount);
        result.put("page", page);
        result.put("pageSize", pageSize);
        result.put("totalPage", (int) Math.ceil((double) totalCount / pageSize));

        return result;
    }


    // 차단 처리(관리자)
    public boolean blockProcess(Map<String, Object> params) {
        // 이미 차단 중인지 중복 체크
        int row = dao.blockDuplicateCheck(params);
        if (row == 0) {
            dao.blockProcess(params);
            // member 테이블에 block_yn 을 true 로 변경
            dao.blockYnUpdate(params);
        }
        return row > 0;
    }

    // 매일 자정마다 실행(자동 차단 해제)
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoUnblockMembersByDate() {
        int result = dao.autoUnblockMembers();
        log.info("자동 차단 해제 회원 수: {}", result);
    }

    // 차단 해제(관리자)
    public boolean blockAdminCancel(Map<String, Object> params) {
        int row = dao.blockAdminCancel(params);
        if (row > 0) {
            // 차단 종료일을 차단을 해제한 날로 변경
            dao.blockAdminEndDateUpdate(params);
        }
        return row > 0;
    }

    // 차단 상세보기
    public Map<String, Object> blockDetail(Map<String, Object> params) {
        return dao.blockDetail(params);
    }
}
