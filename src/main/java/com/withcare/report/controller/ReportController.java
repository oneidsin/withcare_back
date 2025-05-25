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
    public Map<String, Object> reportList(@RequestBody Map<String, Object> params, @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
            result.put("result", svc.reportList());
            login = true;
        }

        result.put("loginYN", login);
        return result;

    }

    // 신고 처리 페이지(관리자)
    @GetMapping("/admin/report/list/view")
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
            @RequestParam(required = false) String category,     // 욕설 등 신고 카테고리
            @RequestParam(required = false) String reportType,   // 게시글, 댓글, 멘션
            @RequestParam(defaultValue = "desc") String sortOrder, // 최신순(desc), 오래된순(asc)
            @RequestHeader Map<String, String> header
    ) {
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

            List<Map<String, Object>> list = svc.reportHistory(params);
            result.put("result", list);
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

    // 신고 카테고리 불러오기
    @GetMapping("/admin/report-manage/report-cate-list")
    public Map<String, Object> reportCateList(@RequestBody Map<String, Object> params,
                                              @RequestHeader Map<String, String> header) {
        log.info("header : {}", header);
        result = new HashMap<>();

        String loginId = (String) JwtToken.JwtUtils.readToken(header.get("authorization")).get("id");
        boolean login = false;

        if (!loginId.equals("") && loginId.equals(params.get("id"))) {
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