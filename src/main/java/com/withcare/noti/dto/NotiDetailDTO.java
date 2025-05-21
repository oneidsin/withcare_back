package com.withcare.noti.dto;

public class NotiDetailDTO {
    private int related_item_id;
    private String related_item_type; // comment/mention/message
    private String sender_name;
    private String receiver_name;
    private String content_pre;

    public int getRelated_item_id() {
        return related_item_id;
    }

    public void setRelated_item_id(int related_item_id) {
        this.related_item_id = related_item_id;
    }

    public String getRelated_item_type() {
        return related_item_type;
    }

    public void setRelated_item_type(String related_item_type) {
        this.related_item_type = related_item_type;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getContent_pre() {
        return content_pre;
    }

    public void setContent_pre(String content_pre) {
        this.content_pre = content_pre;
    }
}
