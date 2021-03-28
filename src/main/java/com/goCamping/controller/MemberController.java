package com.goCamping.controller;

import org.apache.logging.log4j.Logger;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.goCamping.domain.MemberVO;
import com.goCamping.service.MemberService;
import com.goCamping.validator.MemberValidator;

@Controller
@RequestMapping("/member")
public class MemberController {

	static final private Logger logger = LogManager.getLogger(MemberController.class);
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private MemberValidator validator;
	
	@RequestMapping( value="/join", method = RequestMethod.GET )
	public String join(@ModelAttribute MemberVO memberVO) {
		
		logger.info(" join GET 진입");
		
		return "/member/join";
		
	}
	@RequestMapping( value="/join", method = RequestMethod.POST )
	public String join(MemberVO memberVO, BindingResult bindingResult) {
		
		logger.info("join POST 진입");
		
		// 객체의 유효성 검증
		if(bindingResult.hasErrors()) {
			
			logger.info("MemberVO 객체 검증 실패");
			
			// 객체 유효성 검증 실패시 다시 회원가입 view로 이동 ( join.jsp )
			return "/member/join";
		}
		
		
		return "redirect:/";
		
	}
	
	
	
	@RequestMapping("/list")
	public void list() throws Exception{
		
		
		List<MemberVO> list = service.member_list();
		logger.info("조회 완료!");
		
		for (MemberVO memberVO : list) {
			
			System.out.println(memberVO.toString());
			
		}
		
	}
	
}
