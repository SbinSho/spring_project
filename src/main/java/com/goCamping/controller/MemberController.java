package com.goCamping.controller;

import org.apache.logging.log4j.Logger;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goCamping.domain.MemberVO;
import com.goCamping.service.MailService;
import com.goCamping.service.MemberService;
import com.goCamping.validator.MemberValidator;

@Controller
@RequestMapping("/member")
public class MemberController {

	static final private Logger logger = LogManager.getLogger(MemberController.class);
	
	@Autowired
	private MemberService member_service;
	
	@Autowired
	private MailService mail_service;
	
	
	@RequestMapping( value="/join", method = RequestMethod.GET )
	public String join(@ModelAttribute MemberVO memberVO) {
		
		logger.info("join GET 요청");
		
		return "/member/join";
		
	}
	@RequestMapping( value="/join", method = RequestMethod.POST )
	public String join(MemberVO memberVO, BindingResult bindingResult) throws Exception{
		
		logger.info("join POST 요청");
		
		
		// 객체의 유효성 검증
		if(bindingResult.hasErrors()) {
			
			logger.info("MemberVO 객체 검증 실패");
			
			// 객체 유효성 검증 실패시 다시 회원가입 view로 이동 ( join.jsp )
			return "/member/join";
		}
		
		member_service.member_join(memberVO);
		
		
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
