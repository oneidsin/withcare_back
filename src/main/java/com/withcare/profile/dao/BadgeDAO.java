package com.withcare.profile.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BadgeDAO {

    List<Map<String, Object>> getMemberBadgeList(String id);

    List<Map<String, Object>> getMemberAcquiredBadge(String id);

    int isBadgeAcquired(String id, int bdg_idx);

    void insertAcquiredBadge(String id, int bdg_idx);

    int clearAllBadgeSym(String id);

    int updateBadgeSym(String id, int bdg_idx);
}
