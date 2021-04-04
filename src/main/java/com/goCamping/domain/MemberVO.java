package com.goCamping.domain;

import java.sql.Timestamp;

import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.goCamping.dto.EncryptDTO;


public class MemberVO {
	
	
	private String user_id;
	private String user_name;
	private String user_nickname;
	private String user_mail;
	private String user_pwd;
	private Timestamp user_regdate;
	private Boolean business_check;
	
	public MemberVO() {};
	
	public MemberVO(EncryptDTO encryptDTO) {
		this.user_id = encryptDTO.getEn_userID();
		this.user_name = encryptDTO.getEn_userName();
		this.user_nickname = encryptDTO.getEn_userNickName();
		this.user_mail = encryptDTO.getEn_userMail();
		this.user_pwd = encryptDTO.getEn_userPwd();
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
