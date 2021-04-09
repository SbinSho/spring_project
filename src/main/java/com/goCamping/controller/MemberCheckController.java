package com.goCamping.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goCamping.service.MailService;
import com.goCamping.service.MemberService;

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
	public int id_Check(String value) throws Exception {

		logger.info("/idCheck 요청");

		// DB에 user_id 컬럼은 현재 기본키로 잡혀있음, 그러므로 아이디 중복 시 반환값은 1 아닐 경우 0
		return member_service.id_Check(value);

	}

	// 닉네임 중복 확인
	@RequestMapping(value = "/nickCheck", method = RequestMethod.POST)
	public int nick_Check(String value) throws Exception {

		logger.info("/nickCheck 요청");

		// DB에 user_nickname은 기본키이다, 그러므로 아이디 중복시 반환값은 1 아닐 경우 0
		return member_service.nick_Check(value);
	}

	// 메일 중복 확인
	@RequestMapping(value = "/mailCheck", method = RequestMethod.POST)
	public Map<String, String> mail_Check(String user_mail, HttpSession session) throws Exception {

		logger.info("/mailCheck 요청");

		Map<String, String> mail_result = new HashMap<String, String>();


		// mail_result가 1인 경우, 중복된 메일이 존재하는 경우
		// 인증코드 전송 작업을 하지 않고 현재 mail_result 값을 바로 리턴함
		if (mail_service.mail_Check(user_mail) == 1) {
			mail_result.put("result","DUP_MAIL");
			return mail_result;
		}

		// 사용자에게 인증코드 전송하고, 전송된 인증코드를 MailService에서 반환받음
		String auth_key = mail_service.sendAuthMail(user_mail);

		if (auth_key.isEmpty()) {
			mail_result.put("result", "MAIL_ERROR");
			return mail_result;
		}

		// 반환받은 인증코드를 세션에 저장함
		session.setAttribute("auth_key", auth_key);
		mail_result.put("result", "KEY_OK");
		return mail_result;
	}

	// 메일 인증코드 확인 처리
	@RequestMapping(value = "/authCheck", method = RequestMethod.POST)
	public Map<String, String> auth_check(@RequestParam("auth_code") String auth_code, HttpSession session) {

		Map<String, String> auth_result = new HashMap<String, String>();

		// 현재 세션에 저장된 인증코드를 가져온다
		String ses_key = (String) session.getAttribute("auth_key");

		if (ses_key != null) {
			// 세션에 저장된 인증코드와 사용자에게 입력된 인증코드를 비교한다.
			if (ses_key.equals(auth_code)) {

				// 인증코드가 같으면 현재 세션에 저장되어있는 인증코드를 제거한다.
				session.removeAttribute("auth_key");
				auth_result.put("result", "KEY_OK");

			} else {
				auth_result.put("result", "KEY_FAIL");
			}
			
			return auth_result;
		} 
		
		auth_result.put("result", "KEY_ERROR");
		return auth_result;
	}

}
