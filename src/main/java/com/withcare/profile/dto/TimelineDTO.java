package com.withcare.profile.dto;

import java.sql.Date;

public class TimelineDTO {
	
	private int time_idx;
    private String time_user_id;
    private Date day;
    private String time_title;
    private String time_content;
    private Boolean time_public_yn;
    private Date time_create_date;
    private Date time_update_date;
    
	public int getTime_idx() {
		return time_idx;
	}
	public void setTime_idx(int time_idx) {
		this.time_idx = time_idx;
	}
	public String getTime_user_id() {
		return time_user_id;
	}
	public void setTime_user_id(String time_user_id) {
		this.time_user_id = time_user_id;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public String getTime_title() {
		return time_title;
	}
	public void setTime_title(String time_title) {
		this.time_title = time_title;
	}
	public String getTime_content() {
		return time_content;
	}
	public void setTime_content(String time_content) {
		this.time_content = time_content;
	}
	public Boolean getTime_public_yn() {
		return time_public_yn;
	}
	public void setTime_public_yn(Boolean time_public_yn) {
		this.time_public_yn = time_public_yn;
	}
	public Date getTime_create_date() {
		return time_create_date;
	}
	public void setTime_create_date(Date time_create_date) {
		this.time_create_date = time_create_date;
	}
	public Date getTime_update_date() {
		return time_update_date;
	}
	public void setTime_update_date(Date time_update_date) {
		this.time_update_date = time_update_date;
	}
    
    

}
