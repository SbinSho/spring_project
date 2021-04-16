package com.goCamping.service;

import java.util.List;

import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;

public interface BoardService {

	// 총 게시글 조회
	public int BoardCount();

	// 게시글 조회
	public List<BoardVO> board_list(Criteria cri);
	
}
