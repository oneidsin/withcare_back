package com.withcare.profile.dto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public class StageDTO {

	private int stage_idx;
	private String stage_name;

	public int getStage_idx() {
		return stage_idx;
	}

	public void setStage_idx(int stage_idx) {
		this.stage_idx = stage_idx;
	}

	public String getStage_name() {
		return stage_name;
	}

	public void setStage_name(String stage_name) {
		this.stage_name = stage_name;
	}
}
