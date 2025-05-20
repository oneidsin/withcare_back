package com.withcare.profile.dto;

public class BadgeDTO {
    private int bdg_idx;
    private String bdg_name;
    private String bdg_icon;
    private String bdg_condition;
    private boolean bdg_active_yn;

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

    public String getBdg_condition() {
        return bdg_condition;
    }

    public void setBdg_condition(String bdg_condition) {
        this.bdg_condition = bdg_condition;
    }

    public boolean isBdg_active_yn() {
        return bdg_active_yn;
    }

    public void setBdg_active_yn(boolean bdg_active_yn) {
        this.bdg_active_yn = bdg_active_yn;
    }
}
