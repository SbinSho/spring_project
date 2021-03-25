package com.goCamping.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

	static final private Logger logger = LogManager.getLogger(MemberController.class);
	
	
	@RequestMapping("/join.do")
	public String join() {
		
		logger.info(" join 페이지 진입");
		
		return "/member/join";
		
	}
	
}
