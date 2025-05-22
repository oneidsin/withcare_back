package com.withcare.statistic.dto;

public class PostStatDTO {

	private int board_idx;
	private String board_name;
	private boolean com_yn;
	private int post_count;
	private int comment_count;

	public int getBoard_idx() {
		return board_idx;
	}

	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}

	public String getBoard_name() {
		return board_name;
	}

	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}

	public boolean isCom_yn() {
		return com_yn;
	}

	public void setCom_yn(boolean com_yn) {
		this.com_yn = com_yn;
	}

	public int getPost_count() {
		return post_count;
	}

	public void setPost_count(int post_count) {
		this.post_count = post_count;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

}
