package com.goCamping.service;

import java.util.Random;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.goCamping.dao.MemberDAO;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MemberDAO mdao;
	
	@Override
	public String authCode() {
		// Random 객체 생성 
		Random ran = new Random();
		// 랜덤으로 생성된 키를 입력 받기 위한 변수
		String random_key = "";
		
		// 현재시각을 밀리세컨드 단위로 변환하여 입력
		long seed = System.currentTimeMillis();
		// 동일한 난수 발생할 경우를 제거하기 위해, seed값을 설정함
		ran.setSeed(seed);
		
		for(int i = 0; i<5; i++) {
			int index = ran.nextInt(3);
			
			switch (index) {
			case 0:
				// 26 미만의 난수 생성 후 정수 97 더함, 계산이 완료된 값을 char 형변환
				random_key += ((char)((int)(ran.nextInt(26)) + 97));
				break;
			case 1:
				random_key += ((char)((int)(ran.nextInt(26)) + 65));
				break;
			case 2:
				random_key += ran.nextInt(10);
				break;
			}
		}
		return random_key;
	}

	@Override
	public String sendAuthMail(String user_mail) {
		
		String charset = "utf-8";
		// 관리자 메일 주소
		String admin_mail = "tngh234@naver.com";
		
		// MimeMessage 클래스를 이용해 메일 구성
		MimeMessage mime_mail = mailSender.createMimeMessage();
		
		// 인증코드 입력
		String auth_key = authCode();
		
		// 메시지 내용 작성
		String mail_content = "";
		mail_content += "<div align='center' style='border:1px solid black; font-family:verdana'>";
		mail_content += "<h3 style='color:blue;'><strong>goCamping</strong> 회원가입 이메일 인증코드입니다.</h3>";
		mail_content += "<div style='font-size: 130%'>";
		mail_content += "회원가입 페이지로 돌아가 인증코드 <strong>";
		mail_content += auth_key +"</strong>를 입력해주세요.</div><br/>";
		
		try {
			
			// 제목 입력
			mime_mail.setSubject("회원가입 이메일 인증", charset);
			// 보내는 사람 입력 , 보내는 사람 입력 안할 시 오류 발생
			mime_mail.setFrom( admin_mail );
			// 메일 내용 입력
			mime_mail.setText(mail_content, charset, "html");
			// 받는 사람 입력
			mime_mail.addRecipient(Message.RecipientType.TO, new InternetAddress(user_mail));
			// 메시지 전송
			mailSender.send(mime_mail);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		return auth_key;
	}
	
	// 메일 중복확인
	@Override
	public int mail_Check(String user_mail) throws Exception {
		return mdao.mail_Check(user_mail);
	}
	
}
