package com.goCamping.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
	
	/*
	 	AOP 적용으로, 핵심 기능외의 공통적인 기능인 메소드 진입 로그 확인을 주석처리
	 	
		logger.info("/board/list GET 호출");
		logger.info("/board/write GET 진입");
		logger.info("/board/write POST 진입");
		logger.info("/board/edit GET 진입");
		logger.info("/edit POST 진입");
		logger.info("/board/read GET 진입");
		logger.info("/board/fileDownload GET 진입");
		logger.info("/board/delete GET 진입");
		
	*/

	@Autowired
	private BoardService board_service;

	// 전체 게시글 리스트 보기 페이지 이동
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Criteria cri, Model model) {


		// view 페이지에 전달 할 페이지 객체 ( 페이징 처리시 사용 )
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(board_service.BoardCount());

		// 현재 입력 된 페이지 번호의 게시판 가져오기
		List<BoardVO> list = board_service.board_list(cri);

		// 게시판 리스트
		model.addAttribute("list", list);
		// 페이징 처리
		model.addAttribute("pageMaker", pageMaker);
		// 현재 페이지 번호
		model.addAttribute("page", cri.getPage());

		return "/board/list";
	}

	// 게시글 작성 페이지 이동
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(
			BoardWriteDTO boardWriteDTO,
			@RequestParam("page") int page,
			HttpServletRequest request, Model model, RedirectAttributes rttr) {


		// Interceptor에서 세션이 존재하는지 유무를 판단함		
		HttpSession session = request.getSession();
		// 세션에 저장된 정보 가져오기
		AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
		// 세션에 저장된 아이디를 이용해 작성자 셋팅
		boardWriteDTO.setWriter(authInfo.getId());

		model.addAttribute("boardWriteDTO", boardWriteDTO);
		model.addAttribute("page", page);
		
		return "/board/write";

	}

	// 게시글 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@Valid BoardWriteDTO boardWriteDTO, Errors errors,
			MultipartHttpServletRequest multipartHttpServletRequest, RedirectAttributes rttr) {


		// write 페이지 진입 전에는 Interceptor에서 세션이 존재하는지 유무를 판단함		
		HttpSession session = multipartHttpServletRequest.getSession();
		// 세션에 저장된 정보 가져오기
		AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");

		// 객체 유효성 검증
		if (errors.hasErrors()) {

			logger.info("객체 유효성 검증 실패!");
			return "/board/write";

		}
		
		// 현재 세션에 저장된 유저 정보와 글쓰기 요청한 작성자 정보와 일치하는지 체크
		if( !boardWriteDTO.getWriter().equals(authInfo.getId())) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}

		// 첨부파일이 갯수가 6개 초과해서 들어왔는지 아닌지 체크 ( 파일 유효성 검사 ) start
		Boolean file_CountChk_result = false;

		Map<String, MultipartFile> map = multipartHttpServletRequest.getFileMap();

		if (map.size() > 6) {
			file_CountChk_result = true;
		}

		if (file_CountChk_result) {
			logger.info("파일 유효성 검증 실패!");
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}
		// 파일 유효성 검사 end

		// 게시글 작성 처리
		if (!board_service.board_write(boardWriteDTO, multipartHttpServletRequest)) {
			logger.info("게시글 작성 실패!");
			return "redirect:/board/write";
		}

		return "redirect:/board/list";
	}

	// 게시글 수정 페이지 이동
	@RequestMapping(value = "/edit/{bno}", method = RequestMethod.GET)
	public String edit(
			@PathVariable("bno") int bno, 
			@RequestParam("page") int page,
			HttpServletRequest request,
			Model model, RedirectAttributes rttr)
			throws Exception {


		// DB에 저장 된 현재 게시판의 정보 불러오기
		BoardVO boardVO = board_service.board_read(bno);
		
		// Interceptor에서 세션이 존재하는지 유무를 판단함		
		HttpSession session = request.getSession();
		// 세션에 저장된 정보 가져오기
		AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");

		// 게시판이 존재하지 않거나, 현재 요청한 아이디와 세션에 저장 된 아이디가 같은지 비교
		if (boardVO == null || !boardVO.getWriter().equals(authInfo.getId())) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}
		// 현재 게시판 번호에 해당하는 파일들을 불러온다.
		List<Map<String, Object>> board_fileList = board_service.board_fileList(boardVO.getBno());

		// DB 오류로 인한 board_fileList에 null 값이 들어갈 경우 방지
		if (board_fileList == null) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}

		BoardEditDTO boardEditDTO = new BoardEditDTO();

		boardEditDTO.setBno(boardVO.getBno());
		boardEditDTO.setTitle(boardVO.getTitle());
		boardEditDTO.setWriter(boardVO.getWriter());
		boardEditDTO.setContent(boardVO.getContent());

		model.addAttribute("boardEditDTO", boardEditDTO);
		model.addAttribute("board_fileList", board_fileList);
		model.addAttribute("board_FileListCount", board_fileList.size());
		model.addAttribute("page", page);

		return "/board/edit";
	}

	// 게시글 수정
	@RequestMapping(value = "/edit/{bno}", method = RequestMethod.POST)
	public String edit(
			@PathVariable("bno") int bno, 
			@RequestParam("page") int page,
			@Valid BoardEditDTO boardEditDTO, BindingResult bindingResult,
			@RequestParam("array_fileDel[]") String[] del_files,
			MultipartHttpServletRequest multipartHttpServletRequest, RedirectAttributes rttr) {


		// POST 요청으로 들어온 user_id 와 session에 저장된
		// user_id가 일치하는지 인터셉터에서 확인

		// 객체 유효성 검증
		if (bindingResult.hasErrors()) {

			logger.info("객체 유효성 검증 실패!");
			rttr.addFlashAttribute("result", "error");
			return "redirect:/board/edit/" + bno +"?page=" + page;

		}
		
		// Interceptor에서 세션이 존재하는지 유무를 판단함		
		HttpSession session = multipartHttpServletRequest.getSession();
		// 세션에 저장된 정보 가져오기
		AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
		
		// DB에 저장 된 현재 게시판의 정보 불러오기
		BoardVO boardVO = board_service.board_read(bno);

		// 게시판이 존재하지 않거나, 현재 요청한 아이디와 작성자의 아이디가 같은지, 현재 수정하고자 하는 DTO 객체의 게시판 번호(bno) 확인
		if (boardVO == null || !boardVO.getWriter().equals(authInfo.getId()) || boardEditDTO.getBno() != bno) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}

		// 첨부파일이 갯수가 6개 초과해서 들어왔는지 아닌지 체크 ( 파일 유효성 검사 ) start
		Boolean file_CountChk_result = false;

		Map<String, MultipartFile> map = multipartHttpServletRequest.getFileMap();

		if (map.size() > 6) {
			file_CountChk_result = true;
		}

		if (file_CountChk_result) {
			logger.info("파일 유효성 검증 실패!");
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}
		// 파일 유효성 검사 end

		// 요청 받은 게시글 번호 입력
		boardEditDTO.setBno(bno);

		// 게시글 수정 처리
		if (!board_service.board_edit(boardEditDTO, del_files, multipartHttpServletRequest)) {
			logger.info("게시글 수정 실패!");
			return "redirect:/board/edit/" + bno + "?page=" + page;
		}

		return "redirect:/board/list?page=" + page;
	}

	// 게시글 읽기
	@RequestMapping(value = "/read/{bno}", method = RequestMethod.GET)
	public String read(
			@PathVariable("bno") int bno,
			int page,
			Model model, RedirectAttributes rttr) throws Exception {


		BoardVO boardVO = board_service.board_read(bno);

		// 게시판이 존재하지 않는 경우
		if (boardVO == null) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}

		List<Map<String, Object>> board_fileList = board_service.board_fileList(boardVO.getBno());

		// DB 오류로 인한 board_fileList에 null 값이 들어갈 경우 방지
		if (board_fileList == null) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}

		model.addAttribute("board_fileList", board_fileList);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("page", page);

		return "/board/read";
	}

	// 업로드 파일 다운로드
	@RequestMapping(value = "/fileDownload/{file_no}", method = RequestMethod.GET)
	public void board_fileDownload(@PathVariable int file_no, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = board_service.board_fileInfo(file_no);


		String storedFileName = (String) resultMap.get("STORED_FILE_NAME");
		String originalFileName = (String) resultMap.get("ORG_FILE_NAME");

		// 첨부파일을 읽어 byte[]로 변환처리
		byte fileByte[] = org.apache.commons.io.FileUtils
				.readFileToByteArray(new File("c:\\spring_project\\upload\\" + storedFileName));

		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition",
				"attachment; fileName=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\";");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();

	}

	// 게시글 삭제
	@RequestMapping(value = "/delete/{bno}", method = RequestMethod.GET)
	public String delete(
			@PathVariable("bno") int bno, 
			@RequestParam("page") int page,
			HttpServletRequest request,
			RedirectAttributes rttr) {


		BoardVO boardVO = board_service.board_read(bno);
		
		// Interceptor에서 세션이 존재하는지 유무를 판단함		
		HttpSession session = request.getSession();
		// 세션에 저장된 정보 가져오기
		AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");

		if (boardVO == null || !boardVO.getWriter().equals(authInfo.getId())) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}

		HashMap<String, Object> del = new HashMap<String, Object>();
		// 삭제할 게시판 번호와, 작성자 데이터 입력
		del.put("bno", bno);
		del.put("writer", authInfo.getId());

		// 게시판 삭제 처리
		if (!board_service.board_delete(del)) {
			rttr.addFlashAttribute("result", "error");
			return "redirect:/";
		}

		return "redirect:/board/list?page="+page;
	}

}
