package com.withcare.admin.dto;

import java.sql.Date;

// 프론트 할 때 이게 더 편하대요.
// 실제로 여기에 저장되는건 아니고 select 할 때 정보를 담는 바구니 용도로 사용하고 있습니다.
// 실제 정보는 DB에 저장됩니다.
public class AdminMemberDetailDTO {

	private String id;
	private String name;
	private String email;
	private Date join_date;
	private Date access_date;

	private int lv_idx;
	private String lv_name;
	private String lv_icon;

	private boolean admin_yn;
	private boolean block_yn;
	private String block_reason;
	private Date block_start_date;
	private Date block_end_date;

	private String profile_photo;
	private String intro;
	private int access_cnt;
	private boolean profile_yn;

	private int cancer_idx;
	private String cancer_name;

	private int stage_idx;
	private String stage_name;

	private int bdg_idx;
	private String bdg_name;
	private String bdg_icon;
	private boolean bdg_sym_yn;
	
	private String com_content;
	private boolean com_blind_yn;
	private Date com_create_date;
	private Date com_update_date;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getJoin_date() {
		return join_date;
	}
	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}
	public Date getAccess_date() {
		return access_date;
	}
	public void setAccess_date(Date access_date) {
		this.access_date = access_date;
	}
	public int getLv_idx() {
		return lv_idx;
	}
	public void setLv_idx(int lv_idx) {
		this.lv_idx = lv_idx;
	}
	public String getLv_name() {
		return lv_name;
	}
	public void setLv_name(String lv_name) {
		this.lv_name = lv_name;
	}
	public String getLv_icon() {
		return lv_icon;
	}
	public void setLv_icon(String lv_icon) {
		this.lv_icon = lv_icon;
	}
	public boolean isAdmin_yn() {
		return admin_yn;
	}
	public void setAdmin_yn(boolean admin_yn) {
		this.admin_yn = admin_yn;
	}
	public boolean isBlock_yn() {
		return block_yn;
	}
	public void setBlock_yn(boolean block_yn) {
		this.block_yn = block_yn;
	}
	public String getBlock_reason() {
		return block_reason;
	}
	public void setBlock_reason(String block_reason) {
		this.block_reason = block_reason;
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
	public int getAccess_cnt() {
		return access_cnt;
	}
	public void setAccess_cnt(int access_cnt) {
		this.access_cnt = access_cnt;
	}
	public boolean isProfile_yn() {
		return profile_yn;
	}
	public void setProfile_yn(boolean profile_yn) {
		this.profile_yn = profile_yn;
	}
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
	public int getBdg_idx() {
		return bdg_idx;
	}
	public void setBdg_idx(int bdg_idx) {
		this.bdg_idx = bdg_idx;
	}
	public String getBdg_name() {
		return bdg_name;
	}
	public void setBdg_name(String bdg_name) {
		this.bdg_name = bdg_name;
	}
	public String getBdg_icon() {
		return bdg_icon;
	}
	public void setBdg_icon(String bdg_icon) {
		this.bdg_icon = bdg_icon;
	}
	public boolean isBdg_sym_yn() {
		return bdg_sym_yn;
	}
	public void setBdg_sym_yn(boolean bdg_sym_yn) {
		this.bdg_sym_yn = bdg_sym_yn;
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
	public String getCom_content() {
		return com_content;
	}
	public void setCom_content(String com_content) {
		this.com_content = com_content;
	}
	public boolean isCom_blind_yn() {
		return com_blind_yn;
	}
	public void setCom_blind_yn(boolean com_blind_yn) {
		this.com_blind_yn = com_blind_yn;
	}
	public Date getCom_create_date() {
		return com_create_date;
	}
	public void setCom_create_date(Date com_create_date) {
		this.com_create_date = com_create_date;
	}
	public Date getCom_update_date() {
		return com_update_date;
	}
	public void setCom_update_date(Date com_update_date) {
		this.com_update_date = com_update_date;
	}
	
}
