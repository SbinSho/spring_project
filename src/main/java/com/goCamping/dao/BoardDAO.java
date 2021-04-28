package com.goCamping.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;
import com.goCamping.dto.BoardEditDTO;
import com.goCamping.dto.BoardWriteDTO;

public interface BoardDAO {

	// 총 게시글 수 확인
	public int BoardCount();
	// 게시글 리스트 불러오기
	public List<BoardVO> board_list(Criteria cri);
	// 게시글 조회
	public BoardVO board_read(int bno);
	// 조회수 증가
	public Boolean board_view(int bno);
	// 게시글 작성
	public Boolean board_write(BoardWriteDTO boardWriteDTO);
	// 파일 업로드
	public Boolean board_fileUpload(Map<String, Object> map);
	// 업로드 파일 조회
	public List<Map<String, Object>> board_fileList(int bno) throws Exception;
	// 업로드 파일 다운로드
	public Map<String, Object> board_fileInfo(int file_no) throws Exception;
	// 업로드 파일 수정
	public void board_editFile(Map<String, Object> map) throws Exception;
	// 게시글 작성 후 마지막 번호 가져오기
	public int board_getLastBno();
	// 게시글 수정
	public Boolean board_edit(BoardEditDTO boardEditDTO);
	// 게시글 삭제
	public Boolean board_delete(HashMap<String, Object> del);
}
