package com.withcare.msg.dto;

import java.sql.Timestamp;

public class MsgDTO {

	private int msg_idx;
	private String sender_id;
	private String receiver_id;
	private String msg_content;
	private Timestamp msg_sent_at;
	private boolean msg_read;
	private String sender_msg_status;
	private String receiver_msg_status;

	public int getMsg_idx() {
		return msg_idx;
	}

	public void setMsg_idx(int msg_idx) {
		this.msg_idx = msg_idx;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	public String getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public boolean isMsg_read() {
		return msg_read;
	}

	public void setMsg_read(boolean msg_read) {
		this.msg_read = msg_read;
	}

	public String getSender_msg_status() {
		return sender_msg_status;
	}

	public void setSender_msg_status(String sender_msg_status) {
		this.sender_msg_status = sender_msg_status;
	}

	public String getReceiver_msg_status() {
		return receiver_msg_status;
	}

	public void setReceiver_msg_status(String receiver_msg_status) {
		this.receiver_msg_status = receiver_msg_status;
	}

	public Timestamp getMsg_sent_at() {
		return msg_sent_at;
	}

	public void setMsg_sent_at(Timestamp msg_sent_at) {
		this.msg_sent_at = msg_sent_at;
	}

}
