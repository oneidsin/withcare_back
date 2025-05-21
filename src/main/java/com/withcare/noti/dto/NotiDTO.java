package com.withcare.noti.dto;

import java.sql.Timestamp;

public class NotiDTO {
    private int noti_idx;
    private String noti_sender_id;
    private String relate_user_id;
    private String noti_type;
    private int relate_item_id;
    private String content_pre;
    private Timestamp noti_date;
    private boolean noti_read_yn;

    private NotiDetailDTO detail; // 알림 상세 데이터 추가

    public NotiDetailDTO getDetail() {
        return detail;
    }

    public void setDetail(NotiDetailDTO detail) {
        this.detail = detail;
    }

    public int getNoti_idx() {
        return noti_idx;
    }

    public void setNoti_idx(int noti_idx) {
        this.noti_idx = noti_idx;
    }

    public String getNoti_sender_id() {
        return noti_sender_id;
    }

    public void setNoti_sender_id(String noti_sender_id) {
        this.noti_sender_id = noti_sender_id;
    }

    public String getRelate_user_id() {
        return relate_user_id;
    }

    public void setRelate_user_id(String relate_user_id) {
        this.relate_user_id = relate_user_id;
    }

    public String getNoti_type() {
        return noti_type;
    }

    public void setNoti_type(String noti_type) {
        this.noti_type = noti_type;
    }

    public int getRelate_item_id() {
        return relate_item_id;
    }

    public void setRelate_item_id(int relate_item_id) {
        this.relate_item_id = relate_item_id;
    }

    public String getContent_pre() {
        return content_pre;
    }

    public void setContent_pre(String content_pre) {
        this.content_pre = content_pre;
    }

    public Timestamp getNoti_date() {
        return noti_date;
    }

    public void setNoti_date(Timestamp noti_date) {
        this.noti_date = noti_date;
    }

    public boolean isNoti_read_yn() {
        return noti_read_yn;
    }

    public void setNoti_read_yn(boolean noti_read_yn) {
        this.noti_read_yn = noti_read_yn;
    }
}
