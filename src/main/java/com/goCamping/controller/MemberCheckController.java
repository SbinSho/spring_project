package com.goCamping.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goCamping.service.MailService;
import com.goCamping.service.MemberService;
import com.goCamping.validator.MemberCodeCheckValidator;
import com.goCamping.validator.MemberIdCheckValidator;
import com.goCamping.validator.MemberMailCheckValidator;
import com.goCamping.validator.MemberNickCheckValidator;

@RestController
@RequestMapping("/member/check")
public class MemberCheckController {

	@Autowired
	private MemberService member_service;

	@Autowired
	private MailService mail_service;

	private static final Logger logger = LogManager.getLogger(MemberCheckController.class);

	// 아이디 중복 확인
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public int id_Check(@ModelAttribute("value") String value, BindingResult bindingResult) throws Exception {

		logger.info("/idCheck 요청");
		
		// 유효성 검증
		new MemberIdCheckValidator().validate(value, bindingResult);
		
		if(bindingResult.hasErrors()) {
			logger.info("/idCheck 유효성 검증 실패 !");
			return -1;
		}

		return member_service.id_Check(value);

	}

	// 닉네임 중복 확인
	@RequestMapping(value = "/nickCheck", method = RequestMethod.POST)
	public int nick_Check(@ModelAttribute("value") String value, Errors errors) throws Exception {

		logger.info("/nickCheck 요청");
		
		// 유효성 검증
		new MemberNickCheckValidator().validate(value, errors);
		if(errors.hasErrors()) {
			
			logger.info("/nickCheck 유효성 검증 실패 !");
			return -1;
		}

		// DB에 user_nickname은 기본키이다, 그러므로 아이디 중복시 반환값은 1 아닐 경우 0
		return member_service.nick_Check(value);
	}

	// 메일 중복 확인
	@RequestMapping(value = "/mailCheck", method = RequestMethod.POST)
	public Map<String, String> mail_Check(
			@ModelAttribute("user_mail") String user_mail, Errors errors, 
			HttpServletRequest request) throws Exception {

		logger.info("/mailCheck 요청");

		Map<String, String> mail_result = new HashMap<String, String>();

		// 유효성 검증
		new MemberMailCheckValidator().validate(user_mail, errors);
		if(errors.hasErrors()) {
			
			logger.info("/mailCheck 유효성 검증 실패 !");
			mail_result.put("result", "ERROR");
			return mail_result;
		}
		
		// mail_result가 1인 경우, 중복된 메일이 존재하는 경우
		// 인증코드 전송 작업을 하지 않고 현재 mail_result 값을 바로 리턴함
		if (mail_service.mail_Check(user_mail) == 1) {
			mail_result.put("result","DUP_MAIL");
			return mail_result;
			
		}

		HttpSession session = request.getSession(false);
		
		if(session == null) {
			mail_result.put("result", "ERROR");
			return mail_result;
		}

		// 사용자에게 인증코드 전송하고, 전송된 인증코드를 MailService에서 반환받음
		String auth_code = mail_service.sendAuthMail(user_mail);

		if (auth_code.isEmpty()) {
			mail_result.put("result", "MAIL_ERROR");
			return mail_result;
		}

		// 반환받은 인증코드를 세션에 저장함
		session.setAttribute("auth_code", auth_code);
		mail_result.put("result", "CODE_OK");
		
		return mail_result;
	}

	// 메일 인증코드 확인 처리
	@RequestMapping(value = "/authCheck", method = RequestMethod.POST)
	public Map<String, String> auth_check(@ModelAttribute("auth_code") String auth_code, Errors errors, HttpSession session) {

		Map<String, String> auth_result = new HashMap<String, String>();

		new MemberCodeCheckValidator().validate(auth_code, errors);
		
		if(errors.hasErrors()) {
			
			logger.info("/authCheck 유효성 검증 실패 !");
			auth_result.put("result", "ERROR");
			return auth_result;
		}
		
		// 현재 세션에 저장된 인증코드를 가져온다
		String ses_key = (String) session.getAttribute("auth_code");
		
		if (ses_key != null) {
			// 세션에 저장된 인증코드와 사용자에게 입력된 인증코드를 비교한다.
			if (ses_key.equals(auth_code)) {

				auth_result.put("result", "CODE_OK");

			} else {
				auth_result.put("result", "CODE_FAIL");
			}
			
			return auth_result;
		} 
		
		auth_result.put("result", "ERROR");
		return auth_result;
	}

}
