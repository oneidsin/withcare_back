package profile.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import profile.dto.ProfileDTO;

@Mapper
public interface ProfileDAO {
    ProfileDTO getProfileById(String id);
    int updateProfile(ProfileDTO dto);
} 