package com.goCamping.dto;

public class MemberChIdDTO {
	
	private String user_id;
	private String ch_id;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCh_id() {
		return ch_id;
	}
	public void setCh_id(String ch_id) {
		this.ch_id = ch_id;
	}
	
	@Override
	public String toString() {
		return "MemberChIdDTO [user_id=" + user_id + ", ch_id=" + ch_id + "]";
	}
	
	
}
