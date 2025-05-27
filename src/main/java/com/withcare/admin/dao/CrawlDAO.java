package com.withcare.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrawlDAO {
    boolean duplicateUrl(String post_title);

    void insertCrawlPost(Map<String, Object> result);

    void insertCrawlFile(Map<String, Object> result);

    int updateCrawlYn(String id, int sourceIdx);

    int updateCrawlCycle(String id, int sourceIdx, int crawl_cycle);

    List<Map<String, Object>> getCrawlInfo();


}
