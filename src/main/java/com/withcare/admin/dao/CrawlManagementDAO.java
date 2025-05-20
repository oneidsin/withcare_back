package com.withcare.admin.dao;

import com.withcare.admin.dto.CrawlManagementDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrawlManagementDAO {

    List<CrawlManagementDTO> getActiveCrawlSources();

    void updateLastCrawlAt(int source_idx);
}
