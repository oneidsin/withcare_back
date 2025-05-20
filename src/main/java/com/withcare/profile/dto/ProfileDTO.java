package com.withcare.profile.dto;

public class ProfileDTO {

	private String id;
	private int cancer_idx;
	private int stage_idx;
	private String profile_photo;
	private String intro;
	private boolean profile_yn;
	private int accessCnt;

	public int getCancer_idx() {
		return cancer_idx;
	}

	public void setCancer_idx(int cancer_idx) {
		this.cancer_idx = cancer_idx;
	}

	public int getStage_idx() {
		return stage_idx;
	}

	public void setStage_idx(int stage_idx) {
		this.stage_idx = stage_idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProfile_photo() {
		return profile_photo;
	}

	public void setProfile_photo(String profile_photo) {
		this.profile_photo = profile_photo;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public boolean isProfile_yn() {
		return profile_yn;
	}

	public void setProfile_yn(boolean profile_yn) {
		this.profile_yn = profile_yn;
	}

	public int getAccessCnt() {
		return accessCnt;
	}

	public void setAccessCnt(int accessCnt) {
		this.accessCnt = accessCnt;
	}

}
