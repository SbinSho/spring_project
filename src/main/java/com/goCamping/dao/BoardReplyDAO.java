package com.goCamping.dao;

import java.util.List;

import com.goCamping.domain.BoardReplyVO;

public interface BoardReplyDAO {

	// 전체 댓글 갯수 조회
	public int boardReply_count();
	// 전체 댓글 조회
	public List<BoardReplyVO> boardReply_list(int bno);
}
