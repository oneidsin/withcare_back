package com.withcare.report.dto;

import java.sql.Date;

public class BlockListDTO {

	private int block_idx;
	private String blocked_id;
	private String block_admin_id;
	private String block_reason;
	private Date block_start_date;
	private Date block_end_date;

	public int getBlock_idx() {
		return block_idx;
	}

	public void setBlock_idx(int block_idx) {
		this.block_idx = block_idx;
	}

	public String getBlocked_id() {
		return blocked_id;
	}

	public void setBlocked_id(String blocked_id) {
		this.blocked_id = blocked_id;
	}

	public String getBlock_admin_id() {
		return block_admin_id;
	}

	public void setBlock_admin_id(String block_admin_id) {
		this.block_admin_id = block_admin_id;
	}

	public String getBlock_reason() {
		return block_reason;
	}

	public void setBlock_reason(String block_reason) {
		this.block_reason = block_reason;
	}

	public Date getBlock_start_date() {
		return block_start_date;
	}

	public void setBlock_start_date(Date block_start_date) {
		this.block_start_date = block_start_date;
	}

	public Date getBlock_end_date() {
		return block_end_date;
	}

	public void setBlock_end_date(Date block_end_date) {
		this.block_end_date = block_end_date;
	}

}
