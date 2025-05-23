package com.withcare.statistic.dto;

public class MemberStatCntDTO {

	private int total_member; // 전체 회원 수
	private int new_member; // 오늘 신규 가입자 수

	public int getTotal_member() {
		return total_member;
	}

	public void setTotal_member(int total_member) {
		this.total_member = total_member;
	}

	public int getNew_member() {
		return new_member;
	}

	public void setNew_member(int new_member) {
		this.new_member = new_member;
	}
}
