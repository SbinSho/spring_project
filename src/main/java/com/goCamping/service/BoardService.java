package com.goCamping.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;
import com.goCamping.dto.BoardEditDTO;
import com.goCamping.dto.BoardWriteDTO;

public interface BoardService {

	// 총 게시글 수 조회
	public int BoardCount();

	// 게시글 리스트 조회
	public List<BoardVO> board_list(Criteria cri);
	
	// 게시글 조회
	public BoardVO board_read(int bno);
	
	// 게시글 작성
	public Boolean board_write(BoardWriteDTO boardWriteDTO, MultipartHttpServletRequest multipartHttpServletRequest);
	
	// 게시글 수정
	public Boolean board_edit(BoardEditDTO boardEditDTO);
	
	// 게시글 삭제
	public Boolean board_delete(HashMap<String, Object> del);

	// 업로드 파일 조회
	public List<Map<String, Object>> board_fileList(int bno) throws Exception;
	
	// 업로드 파일 다운로드
	public Map<String, Object> board_fileInfo(int file_no) throws Exception;
}
