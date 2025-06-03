package com.withcare.noti.dao;

import com.withcare.noti.dto.NotiDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NotiDAO {
    List<Map<String, Object>> getNoti(Map<String, Object> params);

    int insertNoti(NotiDTO notiDTO);

    Map<String, String> getNotiInfoFromComment(int comIdx);

    int deleteNoti(String id, int noti_idx);

    int deleteAllNoti(String id);

    int readNoti(String id, int noti_idx);

    int getNotiCount(String user_id);

    int deleteOldNoti(String user_id, int count);

    int readAllNoti(String id);

    Integer getPostIdByCommentIdx(int comIdx);

    Integer getPostIdByMentionIdx(int menIdx);
}
