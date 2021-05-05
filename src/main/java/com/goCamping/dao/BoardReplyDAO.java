package com.goCamping.dao;

import java.util.List;
import java.util.Map;

import com.goCamping.domain.BoardReplyVO;
import com.goCamping.dto.BoardReplyEditDTO;
import com.goCamping.dto.BoardReplyWriteDTO;

public interface BoardReplyDAO {

	// 전체 댓글 갯수 조회
	public int boardReply_count(int bno) throws Exception;
	// 전체 댓글 조회
	public List<BoardReplyVO> boardReply_list(Map<String, Object> map) throws Exception;
	// 댓글 작성
	public Boolean reply_write(BoardReplyWriteDTO boardReplyWriteDTO) throws Exception;
	// 댓글 수정
	public Boolean reply_edit(BoardReplyEditDTO boardReplyEditDTO) throws Exception;

}
