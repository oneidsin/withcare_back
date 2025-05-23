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

  int report(Map<String, Object> params);

  // 신고 관련 검증 메서드
  int checkDuplicateReport(Map<String, Object> params);

  String getPostWriter(Integer postIdx);

  boolean checkCategoryValid(Integer categoryIdx);

}
