package com.goCamping.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.goCamping.dto.MemberLoginDTO;
import com.goCamping.dto.MemberJoinDTO;
import com.goCamping.service.EncryptService;
import com.goCamping.service.MailService;
import com.goCamping.service.MemberService;
import com.goCamping.validator.MemberLoginValidator;
import com.goCamping.validator.MemberJoinDTOValidator;

@Controller
@RequestMapping("/member")
public class MemberController {

	private static final Logger logger = LogManager.getLogger(MemberController.class);
	
	@Autowired
	private MemberService member_service;
	
	@Autowired
	private MailService mail_service;
	
	@Autowired
	private EncryptService encrypt_service;
	
	
	// 회원가입 페이지 이동
	@RequestMapping( value="/join", method = RequestMethod.GET )
	public String join(HttpServletRequest request, RedirectAttributes rttr, Model model) throws Exception {
		
		logger.info("join GET 요청");
		
		// 현재 세션 가져오기
		HttpSession session = request.getSession();

		// 개인키 공개키 생성
		Map<String, Object> map = encrypt_service.createKey();
		
		// 생성된 개인키 및 공개키가 존재하지 않으면 null
		if (map != null) {
			
			session.setAttribute("_RSA_WEB_KEY_", map.get("_RSA_WEB_KEY_"));
			request.setAttribute("RSAModulus", map.get("RSAModulus"));
			request.setAttribute("RSAExponent", map.get("RSAExponent"));
			request.setAttribute("memberJoinDTO", new MemberJoinDTO());
			
			
			return "/member/join";
		}
		
		rttr.addFlashAttribute("result", "error");
		
		
		return "redirect:/";
		
	}
	 
	// 회원가입 처리
	@RequestMapping( value="/join", method = RequestMethod.POST )
	public String join(MemberJoinDTO memberJoinDTO, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes rttr) throws Exception{
		
		logger.info("join POST 요청");
		
		// 현재 세션 가져오기
		HttpSession session = request.getSession();
		// 세션에 저장된 개인키를 가져오기
		PrivateKey privateKey = (PrivateKey) session.getAttribute("_RSA_WEB_KEY_");
		
		if(privateKey != null) {
			
			String[] memberJoinDTO_Encrypt_Array = {
					memberJoinDTO.getUser_id(), memberJoinDTO.getUser_name(), memberJoinDTO.getUser_nickname(),
					memberJoinDTO.getUser_mail(), memberJoinDTO.getUser_pwd() };
			
			// 암호화된 객체를 복호화	( 복호화 성공하면 true return )
			if( encrypt_service.decryptRsa(privateKey, memberJoinDTO_Encrypt_Array) ) {
				
				// 복호화된 평문을 다시 객체에 입력 
				memberJoinDTO.setUser_id(memberJoinDTO_Encrypt_Array[0]);
				memberJoinDTO.setUser_name(memberJoinDTO_Encrypt_Array[1]);
				memberJoinDTO.setUser_nickname(memberJoinDTO_Encrypt_Array[2]);
				memberJoinDTO.setUser_mail(memberJoinDTO_Encrypt_Array[3]);
				memberJoinDTO.setUser_pwd(memberJoinDTO_Encrypt_Array[4]);
				
				// 복호화된 객체 유효성 체크
				new MemberJoinDTOValidator().validate(memberJoinDTO, bindingResult);
				
				// 유효성에 문제가 발생하면 새로운 요청을 통해 회원가입 페이지로 이동
				if(bindingResult.hasErrors()) {
					
					logger.info("객체 유효성 검증 실패!");
					
//					rttr.addFlashAttribute("result", "error");
//					return "redirect:/";
					
					return "/member/join";
					
				}
				
				// 복호화된 객체를 이용하여 회원가입 처리
				Boolean DB_result = member_service.member_create(memberJoinDTO);
				
				// 회원가입 완료 체크
				if(DB_result) {
					rttr.addFlashAttribute("result", "member_create_success");
					logger.info("회원가입 처리 완료!");
				} else {
					
					// 현재 DB에서 중복체크 모두 완료 후, 데이터 입력이 되지 않고 오류가 발생할 경우
					rttr.addFlashAttribute("result", "error");
					logger.info("회원가입 오류 발생!");
				}
				
			} else {
				// 암호문 복호화 실패
				rttr.addFlashAttribute("result", "error");
			}
			
			return "redirect:/";
		}
		
		// 개인키가 존재하지 않을 경우 alert로 사용자에게 오류를 알리기 위한 구문
		rttr.addFlashAttribute("result", "error");
		return "redirect:/";
	}
	
	// 로그인 페이지 이동
	@RequestMapping( value = "/login", method = RequestMethod.GET)
	public String login(@CookieValue(value="loginCookie", required = false) Cookie cookie, 
			HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		
		logger.info("login GET 요청");
		
		// 현재 세션 가져오기
		HttpSession session = request.getSession();

		// 개인키 공개키 생성
		Map<String, Object> map = encrypt_service.createKey();
		
		// 생성된 개인키 및 공개키가 존재하지 않으면 null
		if (map != null) {
			
			session.setAttribute("_RSA_WEB_KEY_", map.get("_RSA_WEB_KEY_"));
			request.setAttribute("RSAModulus", map.get("RSAModulus"));
			request.setAttribute("RSAExponent", map.get("RSAExponent"));
			
			// 브라우저에 저장된 cookie 체크
			if(cookie != null) {
				// 브라우저에 저장된 cookie가 존재하면 cookie의 내용을 가져와서 loginDTO id값으로 지정
				request.setAttribute("id", cookie.getValue());
				// 쿠키값을 유지
				request.setAttribute("cookie_check", "checked");
			}
			
			return "/member/login";
		}
		
		rttr.addFlashAttribute("result", "error");		
		return "redirect:/";
	}
	
	// 로그인 처리
	@RequestMapping( value="/login", method = RequestMethod.POST )
	public void login(MemberLoginDTO memberLoginDTO, BindingResult bindingResult, HttpServletRequest request, Model model) throws Exception{
		
		logger.info("login POST 요청");
		
		// 현재 세션 가져오기
		HttpSession session = request.getSession();
		// 세션에 저장된 개인키를 가져오기
		PrivateKey privateKey = (PrivateKey) session.getAttribute("_RSA_WEB_KEY_");	
		
		// 개인키가 존재하는지 체크
		if(privateKey != null) {
			
			String[] memberLoginDTO_Encrypt_Array = { 
					memberLoginDTO.getUser_id(), memberLoginDTO.getUser_pwd(), memberLoginDTO.getUser_reId() };
			
			// 암호화된 객체를 복호화	( 복호화 성공시 true를 반환한다 )
			if(encrypt_service.decryptRsa(privateKey, memberLoginDTO_Encrypt_Array)) {
				
				// 복호화된 평문을 다시 객체에 입력
				memberLoginDTO.setUser_id(memberLoginDTO_Encrypt_Array[0]);
				memberLoginDTO.setUser_pwd(memberLoginDTO_Encrypt_Array[1]);
				memberLoginDTO.setUser_reId( memberLoginDTO_Encrypt_Array[2]);
				
				// 복호화된 객체 유효성 체크
				new MemberLoginValidator().validate(memberLoginDTO, bindingResult);
				
				// 유효성에 문제가 발생하면 새로운 요청을 통해 회원가입 페이지로 이동
				if(bindingResult.hasErrors()) {
					
					logger.info("MemberLoginDTO 객체 유효성 검사 실패");
					// 객체 유효성 검증 실패
					model.addAttribute("check", "fail");
					
				}
				
				boolean DB_result = member_service.member_login(memberLoginDTO);
				
				// DB 아이디 조회 후 클라이언트 요청 아이디와 비밀번호가 일치 할 경우 true
				if(DB_result) {
					return;
				}
				// DB 아이디 비밀번호 매칭 실패
				model.addAttribute("check", "fail");
				
			} 
			// 암호문 복호화 실패
			else {
				model.addAttribute("check", "fail");
			}
			
			
		} else {
			// 개인키가 존재하지 않을 경우
			model.addAttribute("private", "private_error");
			return;
		}
		
	}
	
	//로그아웃 처리
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response){
		
		logger.info("/logout 진입!");
		
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		
		try {
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그아웃 성공!'); location.href='/';</script>");
			out.flush();
			out.close();
			
		} catch (IOException e) {
			logger.info("/logout : PrintWriter 객체 초기화 실패");
			e.printStackTrace();
		}
		
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
