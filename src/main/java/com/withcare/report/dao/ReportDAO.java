package com.withcare.report.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import com.withcare.report.dto.ReportDTO;

@Mapper
public interface ReportDAO {

  List<Map<String, Object>> reportHistory(Map<String, Object> params);

  List<Map<String, Object>> reportList(Map<String, Object> params);

  List<Map<String, Object>> reportCateList();

  int checkDuplicateCate(Map<String, Object> params);

  int reportCateAdd(Map<String, Object> params);

  int checkDuplicateCateForUpdate(Map<String, Object> params);

  int reportCateUpdate(Map<String, Object> params);

  int reportCateActive(Map<String, Object> params);

  String postWriter(int itemIdx);

  String commentWriter(int itemIdx);

  String mentionWriter(int itemIdx);

  boolean isDuplicateReport(String reporterId, String itemType, int itemIdx);

  int report(ReportDTO reportDTO);

  List<Map<String, Object>> reportView(Map<String, Object> params);

  int reportProcess(Map<String, Object> params);

  void reportStatusUpdate(Map<String, Object> params);

  void blindPost(int itemIdx);

  void blindComment(int itemIdx);

  void blindMention(int itemIdx);

  List<Map<String, Object>> reportHistoryDetail(Map<String, Object> params);

  int reportListCount(Map<String, Object> params);

  int reportHistoryCount(Map<String, Object> params);

  List<String> getAdminIds();

  Integer findMenIdxByComIdx(int itemIdx);
}
