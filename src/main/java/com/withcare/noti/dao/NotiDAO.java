package com.withcare.noti.dao;

import com.withcare.noti.dto.NotiDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NotiDAO {
    List<NotiDTO> getNoti(String id);

    String detectItemType(int relateItemId);

    String getMemberName(String notiSenderId);

    int insertNoti(NotiDTO noti);

    Map<String, String> getNotiInfoFromComment(int comIdx);
}
