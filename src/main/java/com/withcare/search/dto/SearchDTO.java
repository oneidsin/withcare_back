package com.withcare.search.dto;

import java.sql.Timestamp;

public class SearchDTO {

	private String user_id;
	private int board_idx;
	private String sch_keyword;
	private Timestamp sch_date;
	private String sch_type;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public Timestamp getSch_date() {
		return sch_date;
	}

	public void setSch_date(Timestamp sch_date) {
		this.sch_date = sch_date;
	}

	public String getSch_type() {
		return sch_type;
	}

	public void setSch_type(String sch_type) {
		this.sch_type = sch_type;
	}
}
