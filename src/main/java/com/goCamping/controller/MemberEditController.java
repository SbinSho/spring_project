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
import com.goCamping.service.EncryptService;
import com.goCamping.service.MemberService;
import com.goCamping.util.AuthInfo;
import com.goCamping.util.CreateKey;
import com.goCamping.validator.MemberChIdCheckValidator;

@Controller
@RequestMapping("/member/edit")
public class MemberEditController {

	private static final Logger logger = LogManager.getLogger(MemberEditController.class);

	@Autowired
	private MemberService member_service;
	
	@Autowired
	private EncryptService encrypt_service;
	
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
		
		// 개인키 공개키 생성
		Map<String, Object> map = encrypt_service.createKey();
		
		
		if(map != null) {
			CreateKey.set(request, map);
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
		
		Map<String, String> chid_result = new HashMap<String, String>();
		
		String[] chIdDTO_array = { memberChIdDTO.getUser_id(), memberChIdDTO.getCh_id() };
		
		// 현재 세션 가져오기 위한 객체 초기화
		HttpSession session = request.getSession(false);
		// 개인키 객체 초기화
		PrivateKey privateKey = session != null ? (PrivateKey) session.getAttribute("_RSA_WEB_KEY_") : null;
		
		if(session == null || privateKey == null) {
			chid_result.put("result", "ERROR");
			return chid_result;
		}
		
		if(encrypt_service.decryptRsa(privateKey, chIdDTO_array)) {
			
			memberChIdDTO.setUser_id(chIdDTO_array[0]);
			memberChIdDTO.setCh_id(chIdDTO_array[1]);
			
			new MemberChIdCheckValidator().validate(memberChIdDTO, errors);
			
			if(errors.hasErrors()) {
				
				chid_result.put("result", "ERROR");
				return chid_result;
				
			}
			
			if(member_service.member_chid(memberChIdDTO)) {
				chid_result.put("result", "OK");
				AuthInfo authInfo = new AuthInfo();
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
	
	// 비밀번호 수정
	@RequestMapping(value = "/chpass", method = RequestMethod.GET)
	public String chpass(String user_id) {
		
		
		
		return "redirect:/";
		
	}
}
	