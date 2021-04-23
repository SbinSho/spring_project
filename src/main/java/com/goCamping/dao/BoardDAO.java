package com.goCamping.dao;

import java.util.HashMap;
import java.util.List;

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
	// 게시글 수정
	public Boolean board_edit(BoardEditDTO boardEditDTO);
	// 게시글 삭제
	public Boolean board_delete(HashMap<String, Object> del);
}
