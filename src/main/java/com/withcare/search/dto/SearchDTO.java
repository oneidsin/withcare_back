package com.withcare.search.dto;

import java.sql.Timestamp;
import java.util.List;

// 검색 테이블을 사용하는 DTO
public class SearchDTO {

	private String sch_id;
	private int board_idx;
	private String sch_keyword;
	private Timestamp sch_date;
	private String sch_type;
	private List<String>keywords; // 게시글 추천용 키워드 리스트
	

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

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
