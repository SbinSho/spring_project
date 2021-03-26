package com.goCamping.domain;

import java.sql.Timestamp;

import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;


public class MemberVO {
	
	
	@Pattern(regexp = "/^[가-힣a-zA-Z0-9]{2,10}$/", message ="2~10 글자의 한글, 영어, 숫자만 사용가능")
	private String user_id;
	
	@Pattern(regexp = "/[0-5]|[가-힣]/", message = "2~5글자의 한글만 입력가능")
	private String user_name;
	
	@Pattern(regexp = "/^[가-힣a-zA-Z0-9]{2,10}$/", message ="2~10 글자의 한글, 영어, 숫자만 사용가능")
	private String user_nickname;
	
	@Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+", message = "ex) example@example.go")
	private String user_mail;
	
	@Pattern(regexp="\"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$\"", message="최소 8자리 숫자, 문자, 특수문자 각각 1개 이상 포함")
	private String user_pwd;
	
	@Past
	Timestamp user_regdate;
	
	@Null
	Boolean business_check;
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public String getUser_mail() {
		return user_mail;
	}
	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public Timestamp getUser_regdate() {
		return user_regdate;
	}
	public void setUser_regdate(Timestamp user_regdate) {
		this.user_regdate = user_regdate;
	}
	public Boolean getBusiness_check() {
		return business_check;
	}
	public void setBusiness_check(Boolean business_check) {
		this.business_check = business_check;
	}
	@Override
	public String toString() {
		return "MemberVO [user_id=" + user_id + ", user_name=" + user_name + ", user_nickname=" + user_nickname
				+ ", user_mail=" + user_mail + ", user_pwd=" + user_pwd + ", user_regdate=" + user_regdate
				+ ", business_check=" + business_check + "]";
	}
	
	

}
