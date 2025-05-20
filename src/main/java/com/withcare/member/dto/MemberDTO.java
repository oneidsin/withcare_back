package com.withcare.member.dto;

import java.util.Date;

public class MemberDTO {
	
	private String id;
	private int lv_idx;
	private String pw;
	private String name;
	private String year;
	private String gender;
	private String email;
	private Date join_date;
	private Date access_date;
	private boolean admin_yn;
	private boolean block_yn;
	private boolean user_del_yn;
	private Date user_del_date;
	private Date lv_date;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLv_idx() {
		return lv_idx;
	}
	public void setLv_idx(int lv_idx) {
		this.lv_idx = lv_idx;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public boolean isUser_del_yn() {
		return user_del_yn;
	}
	public void setUser_del_yn(boolean user_del_yn) {
		this.user_del_yn = user_del_yn;
	}
	public Date getUser_del_date() {
		return user_del_date;
	}
	public void setUser_del_date(Date user_del_date) {
		this.user_del_date = user_del_date;
	}
	public Date getLv_date() {
		return lv_date;
	}
	public void setLv_date(Date lv_date) {
		this.lv_date = lv_date;
	}
	
	

}
