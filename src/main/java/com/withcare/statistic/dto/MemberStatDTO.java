package com.withcare.statistic.dto;

public class MemberStatDTO {

	private String type; // AGE or GENDER
	private String label; // '10대', '20대', '남자', '여자' 등
	private int count; // 각 항목에 해당하는 인원 수

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
