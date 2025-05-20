package com.withcare.report.dto;

public class ReportCateDTO {

	private int rep_cate_idx;
	private String cate_name;
	private boolean cate_active_yn;
	
	
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
	public boolean isCate_active_yn() {
		return cate_active_yn;
	}
	public void setCate_active_yn(boolean cate_active_yn) {
		this.cate_active_yn = cate_active_yn;
	}
	
	
	
}
