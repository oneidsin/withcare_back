package com.withcare.report.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportDAO {

  List<Map<String, Object>> reportCateList();

  int reportCateAdd(Map<String, Object> params);

  int reportCateUpdate(Map<String, Object> params);

  int reportCateActive(Map<String, Object> params);

  int checkDuplicateCate(Map<String, Object> params);

  int checkDuplicateCateForUpdate(Map<String, Object> params);

}
