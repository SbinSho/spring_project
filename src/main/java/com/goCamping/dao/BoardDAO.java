package com.goCamping.dao;

import java.util.List;

import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;

public interface BoardDAO {

	// 총 게시글 수 확인
	public int BoardCount();
	// 게시글 리스트 불러오기
	public List<BoardVO> board_list(Criteria cri);
	// 게시글 조회
	public BoardVO board_read(int bno);
	// 조회수 증가
	public Boolean board_view(int bno);
}
