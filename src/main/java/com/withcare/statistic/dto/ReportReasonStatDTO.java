package com.withcare.statistic.dto;

public class ReportReasonStatDTO {

	private int rep_cate_idx; // 카테고리 인덱스
	private String cate_name; // 카테고리 이름 (예: 욕설, 광고 등)
	private int total_count; // 전체 신고 건수
	private int weekly_count; // 최근 7일 간 신고 건수

	public int getRep_cate_idx() {
		return rep_cate_idx;
	}

	public void setRep_cate_idx(int rep_cate_idx) {
		this.rep_cate_idx = rep_cate_idx;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public int getWeekly_count() {
		return weekly_count;
	}

	public void setWeekly_count(int weekly_count) {
		this.weekly_count = weekly_count;
	}
}
