package com.withcare.profile.dto;

public class MemberBadgeDTO {
    private int user_badge_idx;
    private String id;
    private int bdg_idx;
    private boolean bdg_sym_yn;

    public int getUser_badge_idx() {
        return user_badge_idx;
    }

    public void setUser_badge_idx(int user_badge_idx) {
        this.user_badge_idx = user_badge_idx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBdg_idx() {
        return bdg_idx;
    }

    public void setBdg_idx(int bdg_idx) {
        this.bdg_idx = bdg_idx;
    }

    public boolean isBdg_sym_yn() {
        return bdg_sym_yn;
    }

    public void setBdg_sym_yn(boolean bdg_sym_yn) {
        this.bdg_sym_yn = bdg_sym_yn;
    }
}
