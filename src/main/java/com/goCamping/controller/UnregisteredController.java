package com.goCamping.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.goCamping.domain.UnregisteredVO;

@Controller
@RequestMapping("/unregistered")
public class UnregisteredController {
	
	private static final Logger logger = LogManager.getLogger(UnregisteredController.class);

	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String notice() {
		
		logger.info("/question GET 호출");
		
		return "/unregistered/question";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(String user_id, Model model) {
		
		logger.info("/write GET 진입");
		logger.info("user_id : " + user_id);
		
		model.addAttribute("user_id", user_id);
		
		return "/unregistered/write";
		
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(UnregisteredVO unregisteredVO) {
		
		logger.info("/write POST 진입");
		
		logger.info("unregistered_public : " + unregisteredVO.getUnregistered_public());
		
		return "redirect:/unregistered/question";
		
	}
	
}
