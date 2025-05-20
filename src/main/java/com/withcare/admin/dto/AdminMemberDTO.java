package com.withcare.admin.dto;

import java.sql.Date;

// 회원 정보 + 차단 정보 결합해 보여줄 전용 DTO 생성
// MemberDTO + BlockListDTO 조합은 프론트에서 처리할 때 불편해서 비추천.
//실제로 여기에 저장되는건 아니고 select 할 때 정보를 담는 바구니 용도로 사용하고 있습니다.
//실제 정보는 DB에 저장됩니다.
public class AdminMemberDTO {

    private String id;
    private boolean admin_yn;
    private boolean block_yn;
    private boolean user_del_yn;
    private Date join_date;
    private Date access_date;

    // 차단 정보
    private Date block_start_date;
    private Date block_end_date;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isAdmin_yn() {
		return admin_yn;
	}
	public void setAdmin_yn(boolean admin_yn) {
		this.admin_yn = admin_yn;
	}
	public boolean isBlock_yn() {
		return block_yn;
	}
	public void setBlock_yn(boolean block_yn) {
		this.block_yn = block_yn;
	}
	public boolean isUser_del_yn() {
		return user_del_yn;
	}
	public void setUser_del_yn(boolean user_del_yn) {
		this.user_del_yn = user_del_yn;
	}
	public Date getJoin_date() {
		return join_date;
	}
	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}
	public Date getAccess_date() {
		return access_date;
	}
	public void setAccess_date(Date access_date) {
		this.access_date = access_date;
	}
	public Date getBlock_start_date() {
		return block_start_date;
	}
	public void setBlock_start_date(Date block_start_date) {
		this.block_start_date = block_start_date;
	}
	public Date getBlock_end_date() {
		return block_end_date;
	}
	public void setBlock_end_date(Date block_end_date) {
		this.block_end_date = block_end_date;
	}
    
    
	
}
