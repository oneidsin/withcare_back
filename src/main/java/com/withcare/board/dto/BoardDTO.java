package com.withcare.board.dto;

public class BoardDTO {

	private int board_idx;
	private int lv_idx;
	private int parent_board_idx;
	private String board_name;
	private boolean blind_yn;
	private boolean anony_yn;
	private boolean com_yn;
	
	
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	public int getLv_idx() {
		return lv_idx;
	}
	public void setLv_idx(int lv_idx) {
		this.lv_idx = lv_idx;
	}
	public int getParent_board_idx() {
		return parent_board_idx;
	}
	public void setParent_board_idx(int parent_board_idx) {
		this.parent_board_idx = parent_board_idx;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
	public boolean isBlind_yn() {
		return blind_yn;
	}
	public void setBlind_yn(boolean blind_yn) {
		this.blind_yn = blind_yn;
	}
	public boolean isAnony_yn() {
		return anony_yn;
	}
	public void setAnony_yn(boolean anony_yn) {
		this.anony_yn = anony_yn;
	}
	public boolean isCom_yn() {
		return com_yn;
	}
	public void setCom_yn(boolean com_yn) {
		this.com_yn = com_yn;
	}

	
}
