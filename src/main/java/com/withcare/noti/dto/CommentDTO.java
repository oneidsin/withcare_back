package com.withcare.noti.dto;

public class CommentDTO {
    private int com_idx;
    private int post_idx;
    private String id;
    private String com_create_date;

    public int getCom_idx() {
        return com_idx;
    }

    public void setCom_idx(int com_idx) {
        this.com_idx = com_idx;
    }

    public int getPost_idx() {
        return post_idx;
    }

    public void setPost_idx(int post_idx) {
        this.post_idx = post_idx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCom_create_date() {
        return com_create_date;
    }

    public void setCom_create_date(String com_create_date) {
        this.com_create_date = com_create_date;
    }
}
