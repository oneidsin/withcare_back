package com.withcare.report.service;

import java.util.List;
import java.util.Map;

import com.withcare.report.dto.BlockListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        return row > 0;
    }

    // 차단한 회원 리스트(유저)
    public Map<String, Object> blockList(String id) {
        return dao.blockList(id);
    }

    // 차단 해제(유저)
    public boolean blockCancel(Map<String, Object> params) {
        int row = dao.blockCancel(params);
        return row > 0;
    }

    // 차단 관리(관리자)
    public List<BlockListDTO> getBlockList(Map<String, Object> params) {
        return dao.getBlockList(params);
    }

    // 차단 처리(관리자)
    public boolean blockProcess(Map<String, Object> params) {
        int row = dao.blockProcess(params);
        return row > 0;
    }

    public boolean blockAdminCancel(Map<String, Object> params) {
        int row = dao.blockAdminCancel(params);
        return row > 0;
    }
}
