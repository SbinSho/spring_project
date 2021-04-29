package com.goCamping.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.goCamping.dto.MemberJoinDTO;
import com.goCamping.dto.MemberLoginDTO;
import com.goCamping.service.MemberService;
import com.goCamping.util.CreateKey;
import com.goCamping.util.Encrypt;
import com.goCamping.validator.MemberJoinDTOValidator;
import com.goCamping.validator.MemberLoginValidator;

@Controller
@RequestMapping("/member")
public class MemberController {

	private static final Logger logger = LogManager.getLogger(MemberController.class);

	@Autowired
	private MemberService member_service;
	
	@Autowired
	private Encrypt encrypt;
	
	// 회원가입 페이지 이동
	@RequestMapping( value="/join", method = RequestMethod.GET )
	public String join(HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		
		logger.info("join GET 요청");
		
		// 개인키 공개키 생성
		Map<String, Object> map = encrypt.createKey();
		
		
		if(map != null) {
			CreateKey.set(request, map);
			return "/member/join";
		}
		
		rttr.addFlashAttribute("result", "error");
		return "redirect:/";
	}
	 
	// 회원가입 처리
	@RequestMapping( value="/join", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, String> join(
			@RequestBody MemberJoinDTO memberJoinDTO, // 클라이언트는 요청 처리에 대한 값으로 json 객체를 넘겨줌, 넘어온 객체를 @RequestBody을 이용해서 사용
			BindingResult bindingResult, 
			HttpServletRequest request, 
			RedirectAttributes rttr) throws Exception{
		
		logger.info("join POST 요청");
		
		// 클라이언트에 반환할 데이터
		Map<String, String> join_result = new HashMap<String, String>();
		
		
		// 현재 세션 가져오기 위한 객체 초기화
		HttpSession session = request.getSession(false);
		// 개인키 객체 초기화
		PrivateKey privateKey = session != null ? (PrivateKey) session.getAttribute("_RSA_WEB_KEY_") : null;
		// 세션에 저장된 메일 인증코드
		String seesion_authCode = session != null ? (String) session.getAttribute("auth_code") : null;
		
		if ( session == null || privateKey == null || seesion_authCode == null) {
			
			logger.info("session 생성 OR privateKey 생성 OR seesion_authCode 에러 발생!");
			join_result.put("result","ERROR");
			return join_result;
			
		}
		
		// 클라이언트에게 전달 받은 객체의 데이터를 이용해 문자열 생성
		String[] memberJoinDTO_Encrypt_Array = {
				memberJoinDTO.getUser_id(), memberJoinDTO.getUser_name(), memberJoinDTO.getUser_nickname(),
				memberJoinDTO.getUser_mail(), memberJoinDTO.getUser_pwd(), memberJoinDTO.getAuth_code() };
		
		// 암호화된 객체를 복호화	( 복호화 성공하면 true return )
		if( encrypt.decryptRsa(privateKey, memberJoinDTO_Encrypt_Array) != null) {
			
			// 복호화된 평문을 다시 객체에 입력 
			memberJoinDTO.setUser_id(memberJoinDTO_Encrypt_Array[0]);
			memberJoinDTO.setUser_name(memberJoinDTO_Encrypt_Array[1]);
			memberJoinDTO.setUser_nickname(memberJoinDTO_Encrypt_Array[2]);
			memberJoinDTO.setUser_mail(memberJoinDTO_Encrypt_Array[3]);
			memberJoinDTO.setUser_pwd(memberJoinDTO_Encrypt_Array[4]);
			memberJoinDTO.setAuth_code(memberJoinDTO_Encrypt_Array[5]);
			
			// 복호화된 객체 유효성 체크
			new MemberJoinDTOValidator().validate(memberJoinDTO, bindingResult);
			
			// 유효성 검사 OR 현재 세션에 저장된 인증코드와 클라이언트에 받은 인증코드 일치 여부 확인
			if(bindingResult.hasErrors() || !seesion_authCode.equals(memberJoinDTO.getAuth_code())) {
				
				logger.info("객체 유효성 검증 실패!");
				join_result.put("result", "ERROR");
				return join_result;
				
			} 
			
			// 복호화된 객체를 이용하여 회원가입 처리 ( 처리 불가시 false 반환함 )
			if(member_service.member_create(memberJoinDTO)) {
				logger.info("회원가입 처리 완료!");
				// 세션에 저장된 개인키 및 메일 인증코드 삭제
				join_result.put("result","OK");
				// 현재 생성된 세션 제거
				session.invalidate();
				
			} else {
				// 현재 DB에서 중복체크 모두 완료 후, 데이터 입력이 되지 않고 오류가 발생할 경우
				// 다른 이용자가 먼저 중복체크 후 가입한 상황 OR 클라이언트에서 데이터를 조작해 중복된 값이 넘어오면 안되는데 넘어왔을 경우
				logger.info("회원가입 오류 발생!");
				join_result.put("result", "DB_ERROR");
			}
			
		} 
		else {
			// 암호문 복호화 실패
			logger.info("암호문 복호화 실패!");
			join_result.put("result", "ERROR");
		}
		
		
		return join_result;
	}
	
	// 로그인 페이지 이동
	@RequestMapping( value = "/login", method = RequestMethod.GET)
	public String login(@CookieValue(value="loginCookie", required = false) Cookie cookie, 
			HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		
		logger.info("login GET 요청");
		
		// 개인키 공개키 생성
		Map<String, Object> map = encrypt.createKey();
		
		// 생성된 개인키 및 공개키가 존재하지 않으면 null
		if (map != null) {
			
			CreateKey.set(request, map);
			
			// 브라우저에 저장된 cookie 체크
			if(cookie != null) {
				// 브라우저에 저장된 cookie가 존재하면 cookie의 내용을 가져와서 loginDTO id값으로 지정
				request.setAttribute("id", cookie.getValue());
				// 뷰페이지 체크박스 체크를 위해 값 저장
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
		
		// 현재 세션 가져오기 위한 객체 초기화
		HttpSession session = request.getSession(false);
		// 개인키 객체 초기화
		PrivateKey privateKey = session != null ? (PrivateKey) session.getAttribute("_RSA_WEB_KEY_") : null;
		
		// 개인키가 존재하는지 체크
		if(privateKey != null) {
			// memberLoginDTO 필드에 저장된 값들을 저장할 String 배열
			String[] memberLoginDTO_Encrypt_Array = { 
					memberLoginDTO.getUser_id(), memberLoginDTO.getUser_pwd(), memberLoginDTO.getUser_reId() };
			
			// 암호화된 객체를 복호화	( 복호화 성공시 true를 반환한다 )
			if(encrypt.decryptRsa(privateKey, memberLoginDTO_Encrypt_Array)) {
				
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
				
				// DB 아이디 조회 후 클라이언트 요청 아이디와 비밀번호가 일치 할 경우 true
				if(member_service.member_login(memberLoginDTO)) {
					return;
				}
				// DB 아이디 비밀번호 매칭 실패
				model.addAttribute("check", "fail");
				
			} 
			// 암호문 복호화 실패
			else {
				model.addAttribute("check", "fail");
			}
			
			return;
		}
		
		// 개인키가 존재하지 않을 경우
		model.addAttribute("private", "private_error");
		return;
	}
	
	//로그아웃 처리
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response){
		
		logger.info("/logout 진입!");
		
		response.setContentType("text/html; charset=utf-8");
		
		HttpSession session = request.getSession(false);
		session.removeAttribute("loginUser");
		
		// contextPath 가져오기
		String ContextPath = request.getContextPath() != "" ? request.getContextPath() : "/";
		
		try {
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그아웃 성공!'); location.href='"+ ContextPath + "';</script>");
			out.flush();
			out.close();
			
		} catch (IOException e) {
			logger.info("/logout : PrintWriter 객체 초기화 실패");
			e.printStackTrace();
		}
		
	}
	
	
}
	
