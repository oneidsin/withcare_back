package com.withcare.report.dao;

import java.util.List;
import java.util.Map;

import com.withcare.report.dto.BlockListDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlockDAO {

	int block(Map<String, Object> params);

	List<Map<String, Object>> blockList(Map<String, Object> params);

	int blockListCount(String id);

	int checkIfBlocked(String id, String blocked_id);

	int blockCancel(Map<String, Object> params);

	List<BlockListDTO> getBlockList(Map<String, Object> params);

	int getBlockListCount(Map<String, Object> params);

	int blockProcess(Map<String, Object> params);

	int blockAdminCancel(Map<String, Object> params);

	Map<String, Object> blockDetail(Map<String, Object> params);

	int blockMsgDel(String blockedId);

	void blockYnUpdate(Map<String, Object> params);

	int autoUnblockMembers();

	int blockDuplicateCheck(Map<String, Object> params);

	void blockAdminEndDateUpdate(Map<String, Object> params);
}
