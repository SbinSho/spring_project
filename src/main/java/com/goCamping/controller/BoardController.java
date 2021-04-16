package com.goCamping.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.goCamping.domain.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private static final Logger logger = LogManager.getLogger(BoardController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String notice() {
		
		logger.info("/question GET 호출");
		
		return "/board/list";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(String user_id, Model model) {
		
		logger.info("/write GET 진입");
		logger.info("user_id : " + user_id);
		
		model.addAttribute("user_id", user_id);
		
		return "/board/write";
		
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(BoardVO unregisteredVO) {
		
		logger.info("/write POST 진입");
		
		logger.info("unregistered_public : " + unregisteredVO.getUnregistered_public());
		
		return "redirect:/unregistered/question";
		
	}
	
}
