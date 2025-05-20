package com.withcare.search.dto;

public class SearchResultDTO {

	private String keyword;
	private String search_type; // title, title_content, writer
	private Integer board_idx; // optional

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSearch_type() {
		return search_type;
	}

	public void setSearch_type(String search_type) {
		this.search_type = search_type;
	}

	public Integer getBoard_idx() {
		return board_idx;
	}

	public void setBoard_idx(Integer board_idx) {
		this.board_idx = board_idx;
	}
}
