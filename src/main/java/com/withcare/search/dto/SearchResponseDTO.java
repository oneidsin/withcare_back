package com.withcare.search.dto;

import java.sql.Date;

public class SearchResponseDTO {

	private int post_idx;
	private String post_title;
	private String post_content;
	private String id;
	private int keyword_count;
	private Date created_date;
	private int sch_idx;

	public int getSch_idx() {
		return sch_idx;
	}

	public void setSch_idx(int sch_idx) {
		this.sch_idx = sch_idx;
	}
	public int getPost_idx() {
		return post_idx;
	}

	public void setPost_idx(int post_idx) {
		this.post_idx = post_idx;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getKeyword_count() {
		return keyword_count;
	}

	public void setKeyword_count(int keyword_count) {
		this.keyword_count = keyword_count;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

}
