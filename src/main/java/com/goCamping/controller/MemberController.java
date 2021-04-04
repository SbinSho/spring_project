package com.goCamping.controller;

import java.security.PrivateKey;
import java.util.Map;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goCamping.domain.MemberVO;
import com.goCamping.dto.EncryptDTO;
import com.goCamping.dto.MemberDTO;
import com.goCamping.service.EncryptService;
import com.goCamping.service.MailService;
import com.goCamping.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	static final private Logger logger = LogManager.getLogger(MemberController.class);
	
	@Autowired
	private MemberService member_service;
	
	@Autowired
	private MailService mail_service;
	
	@Autowired
	private EncryptService encrypt_service;
	
	
	// 회원가입 페이지 이동
	@RequestMapping( value="/join", method = RequestMethod.GET )
	public String join(@ModelAttribute MemberDTO memberDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("join GET 요청");
		
		HttpSession session = request.getSession();

		Map<String, Object> map = encrypt_service.createKey();
		
		// 반환된 값 확인 및 타입 확인
		/*for ( String key : map.keySet()) {
			System.out.println(map.get(key).getClass().getName());
			System.out.println("key : " + key + " value : " + map.get(key));
		}*/
		
		if (map != null) {
			
			session.setAttribute("_RSA_WEB_KEY_", map.get("_RSA_WEB_KEY_"));
			request.setAttribute("RSAModulus", map.get("RSAModulus"));
			request.setAttribute("RSAExponent", map.get("RSAExponent"));
			return "/member/join";
		}
		
		
		return "redirect:/";
		
	}
	 
	// 회원가입 처리
	@RequestMapping( value="/join", method = RequestMethod.POST )
	public String join(EncryptDTO encryptDTO, HttpServletRequest request) throws Exception{
		
		logger.info("join POST 요청");
		
		
		
		// 현재 세션 가져온다.
		HttpSession session = request.getSession();
		// 세션에 저장된 개인키를 가져온다.
		PrivateKey privateKey = (PrivateKey) session.getAttribute("_RSA_WEB_KEY_");
		
		
		if(privateKey != null) {
			
			if(encrypt_service.decryptRsa(privateKey, encryptDTO) != null) {
				
				MemberVO memberVO = new MemberVO(encryptDTO);
				member_service.member_join(memberVO);
			};
			
		}
		
		return "redirect:/";
		
	}
	
	
	// 아이디 중복 확인
	@RequestMapping( value = "/idCheck", method = RequestMethod.POST )
	@ResponseBody
	public int id_Check( String user_id ) throws Exception{
		
		logger.info("/idCheck 요청");
		
		// DB에 user_id 컬럼은 현재 기본키로 잡혀있음, 그러므로 아이디 중복 시 반환값은 1 아닐 경우 0 
		return member_service.id_Check(user_id);
		
		
	}
	
	// 닉네임 중복 확인
	@RequestMapping( value = "/nickCheck", method = RequestMethod.POST )
	@ResponseBody
	public int nick_Check( String user_nickname ) throws Exception{
		
		logger.info("/nickCheck 요청");
		
		// DB에 user_nickname은 기본키이다, 그러므로 아이디 중복시 반환값은 1 아닐 경우 0
		return member_service.nick_Check(user_nickname);
	}
	
	// 메일 중복 확인
	@RequestMapping( value = "/mailCheck", method = RequestMethod.POST)
	@ResponseBody
	public int mail_Check( String user_mail, HttpSession session) throws Exception {
		
		logger.info("/mailCheck 요청");
		
		int mail_result = -1;
		
		mail_result = mail_service.mail_Check(user_mail);
		
		// mail_result가 1인 경우, 중복된 메일이 존재하는 경우
		// 인증코드 전송 작업을 하지 않고 현재 mail_result 값을 바로 리턴함
		if(mail_result == 1) {
			return mail_result;
		}
		
		// 사용자에게 인증코드 전송하고, 전송된 인증코드를 MailService에서 반환받음
		String auth_key = mail_service.sendAuthMail(user_mail);
		// 반환받은 인증코드를 세션에 저장함
		session.setAttribute("auth_key", auth_key);
		
		
		return mail_result; 
	}
	
	// 메일 인증코드 확인 처리
	@RequestMapping( value = "/authCheck", method = RequestMethod.POST)
	@ResponseBody
	public int auth_check(@RequestParam("auth_code") String auth_code, HttpSession session) {
		
		int auth_result = 0;
		
		// 현재 세션에 저장된 인증코드를 가져온다
		String ses_key = (String) session.getAttribute("auth_key");
		
		// 세션에 저장된 인증코드와 사용자에게 입력된 인증코드를 비교한다.
		if(ses_key.equals(auth_code)) {
			
			// 인증코드가 같으면 현재 세션에 저장되어있는 인증코드를 제거한다.
			session.removeAttribute("auth_key");
			auth_result = 1;
			
		}
		
		return auth_result;
	}
	
	
}
