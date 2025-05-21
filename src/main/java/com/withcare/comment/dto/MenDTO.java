package com.withcare.comment.dto;

import java.sql.Timestamp;

public class MenDTO {

	private int men_idx;
	private int com_idx;
	private String men_id;
	private String men_writer_id;
	private String men_content;
	private boolean men_blind_yn;
	private Timestamp men_create_date;
	private Timestamp men_update_date;
	
	
	public int getMen_idx() {
		return men_idx;
	}
	public void setMen_idx(int men_idx) {
		this.men_idx = men_idx;
	}
	public int getCom_idx() {
		return com_idx;
	}
	public void setCom_idx(int com_idx) {
		this.com_idx = com_idx;
	}
	public String getMen_id() {
		return men_id;
	}
	public void setMen_id(String men_id) {
		this.men_id = men_id;
	}
	public String getMen_writer_id() {
		return men_writer_id;
	}
	public void setMen_writer_id(String men_writer_id) {
		this.men_writer_id = men_writer_id;
	}
	public String getMen_content() {
		return men_content;
	}
	public void setMen_content(String men_content) {
		this.men_content = men_content;
	}
	public boolean isMen_blind_yn() {
		return men_blind_yn;
	}
	public void setMen_blind_yn(boolean men_blind_yn) {
		this.men_blind_yn = men_blind_yn;
	}
	public Timestamp getMen_create_date() {
		return men_create_date;
	}
	public void setMen_create_date(Timestamp men_create_date) {
		this.men_create_date = men_create_date;
	}
	public Timestamp getMen_update_date() {
		return men_update_date;
	}
	public void setMen_update_date(Timestamp men_update_date) {
		this.men_update_date = men_update_date;
	}

	
	
}
