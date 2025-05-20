package com.withcare.post.dto;

import java.sql.Date;

public class FileDTO {

    private int file_idx;
    private int post_idx;
    private String file_url;
    private Date file_upload_date;
    
    
	public int getFile_idx() {
		return file_idx;
	}
	public void setFile_idx(int file_idx) {
		this.file_idx = file_idx;
	}
	public int getPost_idx() {
		return post_idx;
	}
	public void setPost_idx(int post_idx) {
		this.post_idx = post_idx;
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	public Date getFile_upload_date() {
		return file_upload_date;
	}
	public void setFile_upload_date(Date file_upload_date) {
		this.file_upload_date = file_upload_date;
	}
    
    
}
