package com.goCamping.dto;


public class MemberLoginDTO {
	
	private String user_id;
	private String user_pwd;
	private String user_reId;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_reId() {
		return user_reId;
	}
	public void setUser_reId(String user_reId) {
		this.user_reId = user_reId;
	}
	@Override
	public String toString() {
		return "MemberLoginDTO [user_id=" + user_id + ", user_pwd=" + user_pwd + ", user_reId=" + user_reId + "]";
	}

	
	
	
}
