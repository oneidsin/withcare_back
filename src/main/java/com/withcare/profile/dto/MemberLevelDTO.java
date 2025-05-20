package com.withcare.profile.dto;

// 사용자의 현재 레벨 정보를 담는 DTO
public class MemberLevelDTO {
    private String user_id;
    private int lv_idx;
    private int lv_no;
    private String lv_name;
    private String lv_icon;
    private String intro;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
