package com.withcare.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.withcare.report.dto.ReportDTO;
import com.withcare.report.service.ReportService;
import com.withcare.util.JwtToken;

@CrossOrigin
@RestController
public class ReportController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ReportService svc;

    Map<String, Object> result = null;

    // 신고 관리 페이지(미처리 신고 리스트)
    @GetMapping("/admin/report/list")
    public Map<String, Object> reportList(
            @RequestParam String id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String status, // 추가
            @RequestHeader Map<String, String> header) {
        result = new HashMap<>();
        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("page", page);
            params.put("pageSize", pageSize);
            if (status != null && !status.isEmpty()) { // status 파라미터가 있으면 추가
                params.put("status", status);
            }
            result.put("result", svc.reportList(params));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 신고 처리 페이지(관리자)
    @PostMapping("/admin/report/list/view")
    public Map<String, Object> reportView(@RequestBody Map<String, Object> params,
                                          @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result.put("result", svc.reportView(params));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 신고 처리 진행(관리자)
    @PostMapping("/admin/report/list/view/process")
    public Map<String, Object> reportProcess(@RequestBody Map<String, Object> params,
                                             @RequestHeader Map<String, String> header) {
        log.info("신고 처리 대상 : {}", params);
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result.put("result", svc.reportProcess(params));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 신고 히스토리 불러오기(처리 완료된 신고)
    @GetMapping("/admin/report/history")
    public Map<String, Object> reportHistory(
            @RequestParam String id,
            @RequestParam(required = false) String reporterId,
            @RequestParam(required = false) String reportedId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String reportType,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestHeader Map<String, String> header) {
        Map<String, Object> result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            Map<String, Object> params = new HashMap<>();
            params.put("reporterId", reporterId);
            params.put("reportedId", reportedId);
            params.put("category", category);
            params.put("reportType", reportType);
            params.put("sortOrder", sortOrder);
            params.put("page", page);
            params.put("pageSize", pageSize);

            Map<String, Object> data = svc.reportHistory(params);
            result.put("result", data);
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 신고 히스토리 상세보기
    @PostMapping("/admin/report/history/detail")
    public Map<String, Object> reportHistoryDetail(@RequestBody Map<String, Object> params,
                                                   @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result.put("result", svc.reportHistoryDetail(params));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 신고 히스토리 사유 업데이트
    @PostMapping("/admin/report/history/update")
    public Map<String, Object> reportHistoryUpdate(@RequestBody Map<String, Object> params,
                                                   @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result.put("result", svc.reportHistoryUpdate(params));
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 신고 카테고리 불러오기
    @GetMapping("/admin/report-manage/report-cate-list")
    public Map<String, Object> reportCateList(@RequestParam("id") String id,
                                              @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(id)) {
            result.put("result", svc.reportCateList());
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 신고 카테고리 추가
    @PostMapping("/admin/report-manage/report-cate-add")
    public Map<String, Object> reportCateAdd(@RequestBody Map<String, Object> params,
                                             @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result = svc.reportCateAdd(params);
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 신고 카테고리 수정
    @PutMapping("/admin/report-manage/report-cate-update")
    public Map<String, Object> reportCateUpdate(@RequestBody Map<String, Object> params,
                                                @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result = svc.reportCateUpdate(params);
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    // 신고 카테고리 활성화 여부
    @PutMapping("/admin/report-manage/report-cate-active")
    public Map<String, Object> reportCateActive(@RequestBody Map<String, Object> params,
                                                @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result = svc.reportCateActive(params);
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }

    /* 유저가 신고하는 기능들 */
    // 신고하기
    @PostMapping("/report")
    public Map<String, Object> report(@RequestBody ReportDTO dto,
                                      @RequestHeader Map<String, String> header) {
        log.info("신고 접수 : {}", dto);
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(dto.getReporter_id())) {
            result = svc.report(dto);
            login = true;
        }

        result.put("loginYN", login);
        return result;
    }
}