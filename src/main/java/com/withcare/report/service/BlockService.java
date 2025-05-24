package com.withcare.report.service;

import java.util.Map;

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
        int row = dao.block(params);
		return row > 0;
	}

    public Map<String, Object> blockList(String id) {
        return dao.blockList(id);
    }
}
