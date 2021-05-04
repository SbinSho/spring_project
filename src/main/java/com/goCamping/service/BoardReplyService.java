package com.goCamping.service;

import java.util.List;

import com.goCamping.domain.BoardReplyVO;


public interface BoardReplyService {

	// 전체 댓글 갯수 조회
	public int boardReply_count();
	// 전체 댓글 조회
	public List<BoardReplyVO> boardReply_list(int bno);
	
}
