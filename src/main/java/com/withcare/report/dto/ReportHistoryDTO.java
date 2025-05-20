package com.withcare.report.dto;

import java.sql.Date;

public class ReportHistoryDTO {

	private int rep_list_idx;
	private int rep_idx;
	private String rep_admin_id;
	private Date process_date;
	private boolean rep_yn;
	private String rep_reason;
	
	
	public int getRep_list_idx() {
		return rep_list_idx;
	}
	public void setRep_list_idx(int rep_list_idx) {
		this.rep_list_idx = rep_list_idx;
	}
	public int getRep_idx() {
		return rep_idx;
	}
	public void setRep_idx(int rep_idx) {
		this.rep_idx = rep_idx;
	}
	public String getRep_admin_id() {
		return rep_admin_id;
	}
	public void setRep_admin_id(String rep_admin_id) {
		this.rep_admin_id = rep_admin_id;
	}
	public Date getProcess_date() {
		return process_date;
	}
	public void setProcess_date(Date process_date) {
		this.process_date = process_date;
	}
	public boolean isRep_yn() {
		return rep_yn;
	}
	public void setRep_yn(boolean rep_yn) {
		this.rep_yn = rep_yn;
	}
	public String getRep_reason() {
		return rep_reason;
	}
	public void setRep_reason(String rep_reason) {
		this.rep_reason = rep_reason;
	}
	
}
