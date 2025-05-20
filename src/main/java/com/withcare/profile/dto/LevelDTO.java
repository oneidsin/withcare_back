package com.withcare.profile.dto;

// 현재 레벨 조건을 담는 DTO
public class LevelDTO {
    private int lv_idx;
    private int lv_no;
    private String lv_name;
    private String lv_icon;
    private int post_cnt;
    private int com_cnt;
    private int like_cnt;
    private int time_cnt;
    private int access_cnt;

    public int getLv_idx() {
        return lv_idx;
    }

    public void setLv_idx(int lv_idx) {
        this.lv_idx = lv_idx;
    }

    public int getLv_no() {
        return lv_no;
    }

    public void setLv_no(int lv_no) {
        this.lv_no = lv_no;
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

    public int getPost_cnt() {
        return post_cnt;
    }

    public void setPost_cnt(int post_cnt) {
        this.post_cnt = post_cnt;
    }

    public int getCom_cnt() {
        return com_cnt;
    }

    public void setCom_cnt(int com_cnt) {
        this.com_cnt = com_cnt;
    }

    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public int getTime_cnt() {
        return time_cnt;
    }

    public void setTime_cnt(int time_cnt) {
        this.time_cnt = time_cnt;
    }

    public int getAccess_cnt() {
        return access_cnt;
    }

    public void setAccess_cnt(int access_cnt) {
        this.access_cnt = access_cnt;
    }
}
