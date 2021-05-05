package com.goCamping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.goCamping.domain.BoardReplyVO;
import com.goCamping.domain.Criteria;
import com.goCamping.domain.PageMaker;
import com.goCamping.dto.BoardReplyEditDTO;
import com.goCamping.dto.BoardReplyWriteDTO;
import com.goCamping.service.BoardReplyService;
import com.goCamping.util.AuthInfo;

@RestController
@RequestMapping("/board/reply")
public class BoardReplyController {

	private static final Logger logger = LogManager.getLogger(BoardReplyController.class);
	
	@Autowired
	private BoardReplyService boardReplyService;
	
	@RequestMapping(value="/list/{bno}", method= RequestMethod.GET)
	public Map<String, Object> reply_list(@PathVariable("bno") int bno, Criteria cri) {
		
		logger.info("/board/reply/list GET 호출");
		
		// 현재 게시글의 댓글 갯수 조회
		int replyCount = boardReplyService.boardReply_count(bno);
		
		Map<String, Object> List_map = new HashMap<String, Object>();
		
		List_map.put("bno", bno);
		List_map.put("cri", cri);
		
		// 댓글 조회
		List<BoardReplyVO> list = boardReplyService.boardReply_list(List_map);
		
		// 요청 클라이언트에 반환값을 담을 Map
		Map<String, Object> result_map = new HashMap<String, Object>();
		
		// 댓글 페이징 처리
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardReplyService.boardReply_count(bno));
		
		result_map.put("replyCount", replyCount);
		result_map.put("replyList", list);
		result_map.put("pageMaker", pageMaker);
		
		return result_map;
		
	}
	
	@RequestMapping(value="/write/{bno}", method= RequestMethod.POST)
	public Map<String, String> reply_write(
			@PathVariable("bno") int bno,
			BoardReplyWriteDTO boardReplyWriteDTO, HttpServletRequest request){
		
		logger.info("/board/reply/write POST 호출");
		
		HttpSession session = request.getSession();
		
		Map<String, String> result_map = new HashMap<>();
		
		if(session.getAttribute("loginUser") != null) {
			// 현재 세션에 저장된 정보 가져오기
			AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
			// 작성자를 세션에 저장된 아이디로 지정
			boardReplyWriteDTO.setWriter(authInfo.getId());
			// 현재 게시판 번호 입력
			boardReplyWriteDTO.setBno(bno);
			// 댓글 작성
			if(boardReplyService.reply_write(boardReplyWriteDTO)) {
				result_map.put("result", "OK");
			} else {
				result_map.put("result", "FAIL");
			}
			
		} else {
			result_map.put("result", "ERROR");
		}
		
		return result_map;
	}
	
	@RequestMapping(value="/edit", method= RequestMethod.POST)
	public Map<String, String> reply_edit(
			BoardReplyEditDTO boardReplyEditDTO, HttpServletRequest request){
		
		logger.info("/board/reply/edit POST 호출");
		
		HttpSession session = request.getSession();
		
		Map<String, String> result_map = new HashMap<>();
		
		System.out.println("content : " + boardReplyEditDTO.getContent());
		
		if(session.getAttribute("loginUser") != null) {
			// 현재 세션에 저장된 정보 가져오기
			AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
			// 작성자를 세션에 저장된 아이디로 지정
			boardReplyEditDTO.setWriter(authInfo.getId());
			// 댓글 수정
			if(boardReplyService.reply_edit(boardReplyEditDTO)) {
				result_map.put("result", "OK");
			} else {
				result_map.put("result", "FAIL");
			}
			
		} else {
			result_map.put("result", "ERROR");
		}
		
		return result_map;
	}
}
