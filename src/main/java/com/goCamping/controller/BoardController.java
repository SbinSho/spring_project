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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;
import com.goCamping.domain.PageMaker;
import com.goCamping.dto.BoardEditDTO;
import com.goCamping.dto.BoardWriteDTO;
import com.goCamping.service.BoardService;
import com.goCamping.util.AuthInfo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private static final Logger logger = LogManager.getLogger(BoardController.class);

	@Autowired
	private BoardService board_service;
	
	// 전체 게시글 리스트 보기 페이지 이동
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
	// 게시글 작성 페이지 이동
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(BoardWriteDTO boardWriteDTO, HttpServletRequest request, Model model) {
		
		logger.info("/write GET 진입");
		
		if(boardWriteDTO.getWriter() == null) {
			// 현재 세션 가져오기
			HttpSession session = request.getSession();	
			// 세션에 저장된 정보 가져오기
			AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
			// 세션에 저장된 아이디를 이용해 작성자 셋팅
			boardWriteDTO.setWriter(authInfo.getId());
		}
		
		model.addAttribute("boardWriteDTO", boardWriteDTO);
		
		return "/board/write";
		
	}
	// 게시글 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@Valid BoardWriteDTO boardWriteDTO, BindingResult bindingResult, MultipartHttpServletRequest multipartHttpServletRequest) {
		
		logger.info("/write POST 진입");
		if( bindingResult.hasErrors() ) {
		
			logger.info("객체 유효성 검증 실패!");
			return "/board/write";
		
		}
		
		if(!board_service.board_write(boardWriteDTO, multipartHttpServletRequest)) {
			logger.info("게시글 작성 실패!");
			return "/board/write";
		}
		
		return "redirect:/board/list";
	}
//	// 게시글 작성
//	@RequestMapping(value = "/write", method = RequestMethod.POST)
//	public String write(@Valid BoardWriteDTO boardWriteDTO, BindingResult bindingResult) {
//		
//		logger.info("/write POST 진입");
//		
//		if( bindingResult.hasErrors()) {
//			
//			logger.info("객체 유효성 검증 실패!");
//			return "/board/write";
//			
//		}
//		
//		if(!board_service.board_write(boardWriteDTO)) {
//			logger.info("게시글 작성 실패!");
//			return "/board/write";
//		}
//		
//		logger.info("게시글 작성 성공!");
//		return "redirect:/board/list";
//		
//	}
	// 게시글 수정 페이지 이동
	@RequestMapping(value = "/edit/{bno}", method = RequestMethod.GET)
	public String edit(@PathVariable("bno") int bno, String user_id, Model model, RedirectAttributes rttr) {
		
		logger.info("/edit GET 진입");
		
		BoardVO boardVO = board_service.board_read(bno);
		
		if(!boardVO.getWriter().equals(user_id)) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}
		
		model.addAttribute("boardVO", boardVO);
		
		return "/board/edit";
	}
	// 게시글 수정
	@RequestMapping(value = "/edit/{bno}", method = RequestMethod.POST)
	public String edit(@PathVariable("bno") int bno, BoardEditDTO boardEditDTO, Model model) {
		
		logger.info("/edit POST 진입");
		
		boardEditDTO.setBno(bno);
		
		if(!board_service.board_edit(boardEditDTO)) {
			logger.info("게시글 수정 실패!");
			return "redirect:/board/edit/" + bno +"?user_id=" + boardEditDTO.getWriter();
		}
		
		return "redirect:/board/list";
	}
	
	// 게시글 읽기
	@RequestMapping(value= "/read/{bno}", method = RequestMethod.GET)
	public String read(@PathVariable("bno") int bno, Model model, RedirectAttributes rttr) throws Exception {
		
		logger.info("/read GET 진입");

		
		BoardVO boardVO = board_service.board_read(bno);
		
		if( boardVO == null) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}
		
		List<Map<String, Object>> board_fileList = board_service.board_fileList(boardVO.getBno());
		
		
		for (Map<String, Object> map : board_fileList) {
			System.out.println(map);
		}
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("board_fileList", board_fileList);
		
		return "/board/read";
	}
	
	// 게시글 삭제
	@RequestMapping(value = "/delete/{bno}", method = RequestMethod.GET)
	public String delete(@PathVariable("bno") int bno, String user_id, RedirectAttributes rttr) {
		
		BoardVO boardVO = board_service.board_read(bno);
		
		if(!boardVO.getWriter().equals(user_id)) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}
		
		HashMap<String, Object> del = new HashMap<String, Object>();
		
		del.put("bno", bno);
		del.put("writer", user_id);
		
		if(!board_service.board_delete(del)) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}
		
		return "redirect:/board/list";
	}
	
}
