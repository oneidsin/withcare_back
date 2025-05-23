package com.withcare.statistic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.withcare.statistic.dto.PostBestStatDTO;
import com.withcare.statistic.dto.PostStatDTO;

@Mapper
public interface PostStatDAO {

	int getWeeklyCommentCount();

	int getWeeklyPostCount();

	List<PostStatDTO> getPostAndCom();

	List<PostBestStatDTO> getBestPost();

}
