package com.goCamping.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	private static final Logger logger = LogManager.getLogger(NoticeController.class);

	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public String notice() {
		
		logger.info("/notice GET 호출");
		
		return "/board/notice";
	}
	
}
