package com.withcare.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CrawlDAO {
    boolean duplicateUrl(String post_title);

    void insertCrawlPost(Map<String, Object> result);

    void insertCrawlFile(Map<String, Object> result);
}
