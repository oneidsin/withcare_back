package com.withcare.report.dto;

public class MemberBlockDTO {

	private int user_block_id;
	private String blocker_id;
	private String blocked_id;

	public int getUser_block_id() {
		return user_block_id;
	}

	public void setUser_block_id(int user_block_id) {
		this.user_block_id = user_block_id;
	}

	public String getBlocker_id() {
		return blocker_id;
	}

	public void setBlocker_id(String blocker_id) {
		this.blocker_id = blocker_id;
	}

	public String getBlocked_id() {
		return blocked_id;
	}

	public void setBlocked_id(String blocked_id) {
		this.blocked_id = blocked_id;
	}

}
