package com.withcare.profile.dao;

import com.withcare.profile.dto.LevelDTO;
import com.withcare.profile.dto.MemberActivityDTO;
import com.withcare.profile.dto.MemberLevelDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LevelDAO {

    MemberLevelDTO getMemberLevel(String id);

    MemberActivityDTO getMemberActivity(String id);

    List<LevelDTO> getAllLevels();

    int updateMemberLevel(String id, int lv_idx);

}
