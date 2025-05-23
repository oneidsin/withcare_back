package com.withcare.report.dto;

import java.sql.Timestamp;

public class ReportDTO {

	private int rep_idx;
	private String reporter_id;
	private String reported_id;
	private int rep_cate_idx;
	private String rep_item_type;
	private String status;
	private Timestamp report_at;
	private int rep_item_idx;

	public int getRep_idx() {
		return rep_idx;
	}

	public void setRep_idx(int rep_idx) {
		this.rep_idx = rep_idx;
	}

	public String getReporter_id() {
		return reporter_id;
	}

	public void setReporter_id(String reporter_id) {
		this.reporter_id = reporter_id;
	}

	public String getReported_id() {
		return reported_id;
	}

	public void setReported_id(String reported_id) {
		this.reported_id = reported_id;
	}

	public int getRep_cate_idx() {
		return rep_cate_idx;
	}

	public void setRep_cate_idx(int rep_cate_idx) {
		this.rep_cate_idx = rep_cate_idx;
	}

	public String getRep_item_type() {
		return rep_item_type;
	}

	public void setRep_item_type(String rep_item_type) {
		this.rep_item_type = rep_item_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getReport_at() {
		return report_at;
	}

	public void setReport_at(Timestamp report_at) {
		this.report_at = report_at;
	}

	public int getRep_item_idx() {
		return rep_item_idx;
	}

	public void setRep_item_idx(int rep_item_idx) {
		this.rep_item_idx = rep_item_idx;
	}

}
