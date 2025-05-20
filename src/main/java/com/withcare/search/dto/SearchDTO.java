package com.withcare.search.dto;

import java.sql.Date;

public class SearchDTO {

	private String sch_id;
	private int board_idx;
	private String sch_keyword;
	private Date sch_date;
	private String sch_type;

	public String getSch_id() {
		return sch_id;
	}

	public void setSch_id(String sch_id) {
		this.sch_id = sch_id;
	}

	public int getBoard_idx() {
		return board_idx;
	}

	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}

	public String getSch_keyword() {
		return sch_keyword;
	}

	public void setSch_keyword(String sch_keyword) {
		this.sch_keyword = sch_keyword;
	}

	public Date getSch_date() {
		return sch_date;
	}

	public void setSch_date(Date sch_date) {
		this.sch_date = sch_date;
	}

	public String getSch_type() {
		return sch_type;
	}

	public void setSch_type(String sch_type) {
		this.sch_type = sch_type;
	}
}
