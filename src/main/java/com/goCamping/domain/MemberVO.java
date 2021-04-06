package com.goCamping.domain;

import java.sql.Timestamp;

import com.goCamping.dto.MemberJoinDTO;


public class MemberVO {
	
	
	private String user_id;
	private String user_name;
	private String user_nickname;
	private String user_mail;
	private String user_pwd;
	private Timestamp user_regdate;
	private Boolean business_check;
	
	public MemberVO() {};
	
	public MemberVO(MemberJoinDTO MemberJoinDTO) {
		this.user_id = MemberJoinDTO.getUser_id();
		this.user_name = MemberJoinDTO.getUser_name();
		this.user_nickname = MemberJoinDTO.getUser_nickname();
		this.user_mail = MemberJoinDTO.getUser_mail();
		this.user_pwd = MemberJoinDTO.getUser_pwd();
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
