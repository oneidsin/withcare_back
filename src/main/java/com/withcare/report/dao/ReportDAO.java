package com.withcare.report.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import com.withcare.report.dto.ReportDTO;

@Mapper
public interface ReportDAO {
  // 신고 카테고리 관련
  List<Map<String, Object>> reportCateList();

  int reportCateAdd(Map<String, Object> params);

  int reportCateUpdate(Map<String, Object> params);

  int reportCateActive(Map<String, Object> params);

  int checkDuplicateCate(Map<String, Object> params);

  int checkDuplicateCateForUpdate(Map<String, Object> params);

  int report(ReportDTO reportDTO);

  boolean checkCategoryValid(Integer categoryIdx);

  String postWriter(Integer itemIdx);

  String commentWriter(Integer itemIdx);

  String mentionWriter(Integer itemIdx);

  boolean isDuplicateReport(String reporterId, String itemType, int itemIdx);

  List<Map<String, Object>> reportList();

  void insertReportHistory(Map<String, Object> history);

  List<Map<String, Object>> reportView(Map<String, Object> params);

  int reportProcess(Map<String, Object> params);
}
