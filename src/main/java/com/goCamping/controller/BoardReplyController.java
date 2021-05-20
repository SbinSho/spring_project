package com.goCamping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goCamping.domain.BoardReplyVO;
import com.goCamping.domain.Criteria;
import com.goCamping.domain.PageMaker;
import com.goCamping.dto.BoardReplyDeleteDTO;
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
	
	/*
	 	AOP 적용으로, 핵심 기능외의 공통적인 기능인 메소드 진입 확인 로그를 주석처리
	 	
		logger.info("/board/reply/list GET 호출");
		logger.info("/board/reply/write POST 호출");
		logger.info("/board/reply/edit POST 호출");
		logger.info("/board/reply/edit POST 호출");
	*/
	
	@GetMapping("/list/{bno}")
	public Map<String, Object> reply_list(@PathVariable("bno") int bno, Criteria cri) {
		

		
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
	
	@PostMapping("/write/{bno}")
	public Map<String, String> reply_write(
			@Valid @RequestBody BoardReplyWriteDTO boardReplyWriteDTO, Errors errors,
			@PathVariable("bno") int bno,
			HttpServletRequest request){
		
		
		Map<String, String> result_map = new HashMap<>();
		
		if(errors.hasErrors()) {
			logger.info("객체 유효성 검증 실패!");
			result_map.put("result", "ERROR");
			return result_map;
		}
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUser") != null) {
			// 현재 세션에 저장된 정보 가져오기
			AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
			// 작성자를 세션에 저장된 아이디로 지정
			boardReplyWriteDTO.setWriter(authInfo.getId());
			// 현재 게시판 번호 입력
			boardReplyWriteDTO.setBno(bno);
			// 댓글 작성
			if(boardReplyService.reply_write(boardReplyWriteDTO)) {
				logger.info("댓글 작성 성공!");
				result_map.put("result", "OK");
			} else {
				logger.info("댓글 작성 실패!");
				result_map.put("result", "FAIL");
			}
			
		} else {
			result_map.put("result", "ERROR");
		}
		
		return result_map;
	}
	
	@PutMapping("/edit")
	public Map<String, String> reply_edit(
			@Valid @RequestBody BoardReplyEditDTO boardReplyEditDTO, BindingResult bindingResult, 
			HttpServletRequest request){
		
		
		Map<String, String> result_map = new HashMap<>();
		
		if(bindingResult.hasErrors()) {
			logger.info("객체 유효성 검증 실패!");
			result_map.put("result", "ERROR");
			System.out.println("error : " + bindingResult.getAllErrors());
			return result_map;
		}
		
		HttpSession session = request.getSession();
		
		
		if(session.getAttribute("loginUser") != null) {
			// 현재 세션에 저장된 정보 가져오기
			AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
			// 작성자를 세션에 저장된 아이디로 지정
			boardReplyEditDTO.setWriter(authInfo.getId());
			// 댓글 수정
			if(boardReplyService.reply_edit(boardReplyEditDTO)) {
				logger.info("댓글 수정 성공!");
				result_map.put("result", "OK");
			} else {
				logger.info("댓글 수정 실패!");
				result_map.put("result", "FAIL");
			}
			
		} else {
			result_map.put("result", "ERROR");
		}
		
		return result_map;
	}
	
	@DeleteMapping("/delete")
	public Map<String, String> reply_delete(
			@Valid @RequestBody BoardReplyDeleteDTO boardReplyDeleteDTO, BindingResult bindingResult, 
			HttpServletRequest request){
		
		
		Map<String, String> result_map = new HashMap<>();
		
		HttpSession session = request.getSession();
		
		if(bindingResult.hasErrors()) {
			logger.info("객체 유효성 검증 실패!");
			result_map.put("result", "ERROR");
			return result_map;
		}
		
		if(session.getAttribute("loginUser") != null) {
			// 현재 세션에 저장된 정보 가져오기
			AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
			
			// 현재 요청 받은 작성자와 현재 세션에 저장된 작성자의 아이디가 일치하는지 확인
			if(boardReplyDeleteDTO.getWriter().equals(authInfo.getId())) {
				// 댓글 수정
				if(boardReplyService.reply_delete(boardReplyDeleteDTO)) {
					logger.info("댓글 삭제 성공!");
					result_map.put("result", "OK");
				} else {
					logger.info("댓글 삭제 실패!");
					result_map.put("result", "FAIL");
				}
			} else {
				logger.info("클라이언트 요청 작성자와 세션 작성자 다름!");
				result_map.put("result", "ERROR");
			}
			
			
		} else {
			result_map.put("result", "ERROR");
		}
		
		return result_map;
	}
}
