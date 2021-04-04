package com.goCamping.dto;

import java.sql.Timestamp;

import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;


public class MemberDTO {
	
	
	private String user_id;
	private String user_name;
	private String user_nickname;
	private String user_mail;
	private String user_pwd;
	private Timestamp user_regdate;
	private Boolean business_check;
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public void setUser_regdate(Timestamp user_regdate) {
		this.user_regdate = user_regdate;
	}
	public void setBusiness_check(Boolean business_check) {
		this.business_check = business_check;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public String getUser_mail() {
		return user_mail;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public Timestamp getUser_regdate() {
		return user_regdate;
	}
	public Boolean getBusiness_check() {
		return business_check;
	}
	
	@Override
	public String toString() {
		return "MemberVO [user_id=" + user_id + ", user_name=" + user_name + ", user_nickname=" + user_nickname
				+ ", user_mail=" + user_mail + ", user_pwd=" + user_pwd + ", user_regdate=" + user_regdate
				+ ", business_check=" + business_check + "]";
	}
	
	

}
