package com.withcare.post.dto;

import java.sql.Timestamp;

public class PostDTO {

	private int post_idx;
	private int board_idx;
	private String id;
	private String post_title;
	private String post_content;
	private int post_view_cnt;
	private Boolean com_yn;
	private Timestamp post_create_date;
	private Timestamp post_update_date;
	private boolean post_blind_yn;
	private boolean anony_yn;
	
	public boolean isAnony_yn() {
		return anony_yn;
	}

	public void setAnony_yn(boolean anony_yn) {
		this.anony_yn = anony_yn;
	}

	public int getPost_idx() {
		return post_idx;
	}

	public void setPost_idx(int post_idx) {
		this.post_idx = post_idx;
	}

	public int getBoard_idx() {
		return board_idx;
	}

	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPost_title() {
		return post_title;
	}

	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}

	public String getPost_content() {
		return post_content;
	}

	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}

	public int getPost_view_cnt() {
		return post_view_cnt;
	}

	public void setPost_view_cnt(int post_view_cnt) {
		this.post_view_cnt = post_view_cnt;
	}

	public boolean isPost_blind_yn() {
		return post_blind_yn;
	}

	public void setPost_blind_yn(boolean post_blind_yn) {
		this.post_blind_yn = post_blind_yn;
	}

	public Boolean getCom_yn() {
		return com_yn;
	}

	public void setCom_yn(Boolean com_yn) {
		this.com_yn = com_yn;
	}

	public Timestamp getPost_create_date() {
		return post_create_date;
	}

	public void setPost_create_date(Timestamp post_create_date) {
		this.post_create_date = post_create_date;
	}

	public Timestamp getPost_update_date() {
		return post_update_date;
	}

	public void setPost_update_date(Timestamp post_update_date) {
		this.post_update_date = post_update_date;
	}

}
