package com.goCamping.service;

import java.util.List;
import java.util.Map;

import com.goCamping.domain.BoardReplyVO;
import com.goCamping.dto.BoardReplyEditDTO;
import com.goCamping.dto.BoardReplyWriteDTO;


public interface BoardReplyService {

	// 전체 댓글 갯수 조회
	public int boardReply_count(int bno);
	// 전체 댓글 조회
	public List<BoardReplyVO> boardReply_list(Map<String, Object> map);
	// 댓글 작성
	public Boolean reply_write(BoardReplyWriteDTO boardReplyWriteDTO);
	// 댓글 수정
	public Boolean reply_edit(BoardReplyEditDTO boardReplyEditDTO);
}
