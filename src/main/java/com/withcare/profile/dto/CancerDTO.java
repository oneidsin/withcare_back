package com.withcare.profile.dto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public class CancerDTO {

	private int cancer_idx;
	private String cancer_name;

	public int getCancer_idx() {
		return cancer_idx;
	}

	public void setCancer_idx(int cancer_idx) {
		this.cancer_idx = cancer_idx;
	}

	public String getCancer_name() {
		return cancer_name;
	}

	public void setCancer_name(String cancer_name) {
		this.cancer_name = cancer_name;
	}

}
