package com.goCamping.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;
import com.goCamping.domain.PageMaker;
import com.goCamping.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private static final Logger logger = LogManager.getLogger(BoardController.class);

	@Autowired
	private BoardService board_service;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String notice(Criteria cri, Model model) {
		
		logger.info("/list GET 호출");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(board_service.BoardCount());
		
		List<BoardVO> list = board_service.board_list(cri);
		
		if(list == null) {
			System.out.println("list null");
		}
		
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		
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
		
		
		return "redirect:/unregistered/question";
		
	}
	
	@RequestMapping(value= "/read/{bno}", method = RequestMethod.GET)
	public String read(@PathVariable("bno") int bno, Model model) {
		
		
		BoardVO boardVO = board_service.board_read(bno);
		
		if( boardVO == null) {
			return "redirect:/";
		}
		
		model.addAttribute("boardVO", boardVO);
		
		return "/board/read";
	}
	
}
