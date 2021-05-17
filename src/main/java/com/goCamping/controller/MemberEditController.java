package com.goCamping.controller;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.goCamping.dto.MemberChIdDTO;
import com.goCamping.dto.MemberChPassDTO;
import com.goCamping.dto.MemberDeleteDTO;
import com.goCamping.service.MemberService;
import com.goCamping.util.AuthInfo;
import com.goCamping.util.CreateKey;
import com.goCamping.util.Encrypt;
import com.goCamping.validator.MemberChIdDTOValidator;
import com.goCamping.validator.MemberChPassDTOValidator;
import com.goCamping.validator.MemberDeleteDTOValidator;

@Controller
@RequestMapping("/member/edit")
public class MemberEditController {

	private static final Logger logger = LogManager.getLogger(MemberEditController.class);

	@Autowired
	private MemberService member_service;
	
	@Autowired
	private Encrypt encrypt;
	
	// 회원정보 수정 페이지 이동
	@RequestMapping( value = "/info", method = RequestMethod.GET)
	public String edit(String user_id, Model model) {
		
		logger.info("/edit GET 진입");
		
		model.addAttribute("memberVO", member_service.member_select(user_id));
		
		return "/member/info";
	}
	
	// 아이디 수정 페이지 이동
	@RequestMapping( value = "/chid", method = RequestMethod.GET)
	public String chid(String user_id, Model model, HttpServletRequest request, RedirectAttributes rttr) {
		
		logger.info("/chid GET 진입");
		
		if(encrypt.createKey() != null) {
			// 개인키 공개키 생성
			CreateKey.set(request, encrypt.createKey());
			model.addAttribute("user_id", user_id);
			return "/member/chid";
		}
		
		rttr.addFlashAttribute("result", "error");
		return "redirect:/";
	}
	
	// 아이디 수정 
	@RequestMapping( value = "/chid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> chid(
			@RequestBody MemberChIdDTO memberChIdDTO, Errors errors, HttpServletRequest request ) throws Exception {
		
		logger.info("/chid POST 진입");

		// 클라이언트에 요청에 대한 처리를 위한 Map
		Map<String, String> chid_result = new HashMap<String, String>();
		
		// 암호문 복호화를 위해 string 배열 선언
		String[] chIdDTO_array = { memberChIdDTO.getCh_id() };
		
		// 현재 세션 가져오기 위한 객체 초기화
		HttpSession session = request.getSession(false);
		// 개인키 객체 초기화
		PrivateKey privateKey = session != null ? (PrivateKey) session.getAttribute("_RSA_WEB_KEY_") : null;
		
		if(session == null || privateKey == null) {
			chid_result.put("result", "ERROR");
			return chid_result;
		}
		
		// 복호화
		if(encrypt.decryptRsa(privateKey, chIdDTO_array)) {
			
			// 세션에 저장된 아이디 불러오기
			AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
			
			// 복호화 된 평문을 다시 객체에 입력
			memberChIdDTO.setUser_id(authInfo.getId());
			memberChIdDTO.setCh_id(chIdDTO_array[0]);
			
			// 유효성 검증
			new MemberChIdDTOValidator().validate(memberChIdDTO, errors);
			if(errors.hasErrors()) {
				
				chid_result.put("result", "ERROR");
				return chid_result;
				
			}
			
			if(member_service.member_chid(memberChIdDTO)) {
				chid_result.put("result", "OK");
				// 세션에 저장된 값 변경
				authInfo.setId(memberChIdDTO.getCh_id());
				session.setAttribute("loginUser", authInfo);
			} 
			else {
				chid_result.put("result", "DB_ERROR");
			}
			
		} 
		
		else {
			chid_result.put("result", "ERROR");
		}
		
		return chid_result;
	}
	
	// 비밀번호 수정 페이지 이동
	@RequestMapping(value = "/chpass", method = RequestMethod.GET)
	public String chpass(String user_id, HttpServletRequest request, Model model) {
		
		logger.info("/chpass GET 진입");
		
		if(encrypt.createKey() != null) {
			// 개인키 공개키 생성
			CreateKey.set(request, encrypt.createKey());
			model.addAttribute("user_id", user_id);
			return "/member/chpass";
		}
		
		return "redirect:/";
		
	}
	
	// 비밀번호 수정
	@RequestMapping(value = "/chpass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> chpass(@RequestBody MemberChPassDTO memberChPassDTO, Errors errors
			, HttpServletRequest request, Model model) throws Exception {
		
		logger.info("/chpass POST 진입");
		// 클라이언트에 요청에 대한 처리를 위한 Map
		Map<String, String> chpass_result = new HashMap<String, String>();
		
		// 암호문 복호화를 위해 string 배열 선언
		String[] chPssDTO_array = { memberChPassDTO.getUser_pwd(), memberChPassDTO.getCh_user_pwd() };
		
		// 현재 세션 가져오기 위한 객체 초기화
		HttpSession session = request.getSession(false);
		// 개인키 객체 초기화
		PrivateKey privateKey = session != null ? (PrivateKey) session.getAttribute("_RSA_WEB_KEY_") : null;
		
		if(session == null || privateKey == null) {
			chpass_result.put("result", "ERROR");
			return chpass_result;
		}
		
		// 복호화
		if(encrypt.decryptRsa(privateKey, chPssDTO_array)) {
			
			// 세션에 저장된 아이디 불러오기
			AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
			// 복호화된 평문을 다시 객체에 입력
			memberChPassDTO.setUser_id(authInfo.getId());
			memberChPassDTO.setUser_pwd(chPssDTO_array[0]);
			memberChPassDTO.setCh_user_pwd(chPssDTO_array[1]);
			// 유효성 검증
			new MemberChPassDTOValidator().validate(memberChPassDTO, errors);
			if(errors.hasErrors()) {
				logger.info("객체 유효성 검증 실패!");
				chpass_result.put("result", "ERROR");
				return chpass_result;
				
			}
			
			if(member_service.member_chpass(memberChPassDTO)) {
				
				logger.info("비밀번호 변경 완료!");
				chpass_result.put("result", "OK");
				
			} 
			else {
				
				logger.info("비밀번호 변경 실패!");
				chpass_result.put("result", "DB_ERROR");
				
			}
			
		} 
		
		else {
			logger.info("암호문 복호와 실패!");
			chpass_result.put("result", "ERROR");
		}
		
		return chpass_result;
		
	}
	
	// 회원탈퇴 페이지 이동
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String user_id, HttpServletRequest request, Model model) throws Exception {
		
		logger.info("/chpass GET 진입");
		
		if(encrypt.createKey() != null) {
			// 개인키 공개키 생성
			CreateKey.set(request, encrypt.createKey());
			model.addAttribute("user_id", user_id);
			return "/member/delete";
		}
		
		return "redirect:/";
		
	}
	
	// 회원탈퇴 
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(
			@RequestBody MemberDeleteDTO memberDeleteDTO, Errors errors
			, HttpServletRequest request, Model model) throws Exception {
		
		logger.info("/chpass POST 진입");
		// 클라이언트에 요청에 대한 처리를 위한 Map
		Map<String, String> chpass_result = new HashMap<String, String>();
		// 암호문 복호화를 위해 string 배열 선언
		String[] DelteDTO_array = { memberDeleteDTO.getUser_pwd() };
		
		// 현재 세션 가져오기 위한 객체 초기화
		HttpSession session = request.getSession(false);
		// 개인키 객체 초기화
		PrivateKey privateKey = session != null ? (PrivateKey) session.getAttribute("_RSA_WEB_KEY_") : null;

		if(session == null || privateKey == null) {
			chpass_result.put("result", "ERROR");
			return chpass_result;
		}
		
		// 복호화
		if(encrypt.decryptRsa(privateKey, DelteDTO_array)) {
			
			// 세션에 저장된 아이디 불러오기
			AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
			
			// 복호화된 평문을 이용해 객체 입력
			memberDeleteDTO.setUser_id(authInfo.getId());
			memberDeleteDTO.setUser_pwd(DelteDTO_array[0]);
			
			// 복호화된 객체 유효성 체크
			new MemberDeleteDTOValidator().validate(memberDeleteDTO, errors);
			
			// 유효성 검증
			if(errors.hasErrors()) {
				logger.info("객체 유효성 검증 실패!");
				chpass_result.put("result", "ERROR");
				return chpass_result;
				
			}
			// 회원 탈퇴 처리
			if(member_service.member_delete(memberDeleteDTO)) {
				
				logger.info("회원 탈퇴 변경 완료!");
				session.invalidate();
				chpass_result.put("result", "OK");
				
			} 
			else {
				
				logger.info("비밀번호 변경 실패!");
				chpass_result.put("result", "DB_ERROR");
				
			}
			
		} 
		
		else {
			logger.info("암호문 복호와 실패!");
			chpass_result.put("result", "ERROR");
		}
		
		return chpass_result;
		
	}

}
	
