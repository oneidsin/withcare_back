package com.withcare.comment.dto;

import java.sql.Timestamp;

public class ComDTO {

	private int com_idx;
	private int post_idx;
	private String id;
	private String com_content;
	private boolean com_blind_yn;
	private Timestamp com_create_date;
	private Timestamp com_update_date;
	private boolean com_anony_yn;
	
	
	public int getCom_idx() {
		return com_idx;
	}
	public void setCom_idx(int com_idx) {
		this.com_idx = com_idx;
	}
	public int getPost_idx() {
		return post_idx;
	}
	public void setPost_idx(int post_idx) {
		this.post_idx = post_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCom_content() {
		return com_content;
	}
	public void setCom_content(String com_content) {
		this.com_content = com_content;
	}
	public boolean isCom_blind_yn() {
		return com_blind_yn;
	}
	public void setCom_blind_yn(boolean com_blind_yn) {
		this.com_blind_yn = com_blind_yn;
	}
	public Timestamp getCom_create_date() {
		return com_create_date;
	}
	public void setCom_create_date(Timestamp com_create_date) {
		this.com_create_date = com_create_date;
	}
	public Timestamp getCom_update_date() {
		return com_update_date;
	}
	public void setCom_update_date(Timestamp com_update_date) {
		this.com_update_date = com_update_date;
	}
	public boolean isCom_anony_yn() {
		return com_anony_yn;
	}
	public void setCom_anony_yn(boolean com_anony_yn) {
		this.com_anony_yn = com_anony_yn;
	}
	
}
