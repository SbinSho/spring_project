package com.goCamping.service;

public interface MailService {

	// 메일 인증코드 생성
	public String authCode();
	
	// 메일 인증코드 전송
	public String sendAuthMail(String user_mail);
	
	// 이메일 중복확인
	public int mail_Check(String user_mail) throws Exception;
}
