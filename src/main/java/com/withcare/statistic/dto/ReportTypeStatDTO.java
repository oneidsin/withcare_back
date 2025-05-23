package com.withcare.statistic.dto;

public class ReportTypeStatDTO {

	private String rep_item_type;
	private int total_count;
	private int weekly_count;

	public String getRep_item_type() {
		return rep_item_type;
	}

	public void setRep_item_type(String rep_item_type) {
		this.rep_item_type = rep_item_type;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public int getWeekly_count() {
		return weekly_count;
	}

	public void setWeekly_count(int weekly_count) {
		this.weekly_count = weekly_count;
	}

}
