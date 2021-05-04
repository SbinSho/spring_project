package com.goCamping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.goCamping.domain.BoardReplyVO;
import com.goCamping.service.BoardReplyService;

@RestController
@RequestMapping("/board/reply")
public class BoardReplyController {

	@Autowired
	private BoardReplyService boardReplyService;
	
	@RequestMapping(value="/list/{bno}", method= RequestMethod.GET)
	public List<BoardReplyVO> reply_list(@PathVariable("bno") int bno) {
		
		System.out.println("/board/reply/list GET 호출");
		
		System.out.println("bno : " + bno);
		
		System.out.println("Count : " + boardReplyService.boardReply_count());

		List<BoardReplyVO> list = boardReplyService.boardReply_list(bno);
		
		for (BoardReplyVO boardReplyVO : list) {
			System.out.println(boardReplyVO.toString());
		}
		
		return list;
		
	}
	
}
