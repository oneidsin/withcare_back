package com.withcare.search.dto;

import java.sql.Timestamp;

// 검색하여 나오는 내용들을 끓어오는 DTO
public class SearchResultDTO {

	private int post_idx;
	private int board_idx;
	private String title;
	private String content;
	private String writer;
	private Timestamp created_date;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}
}
