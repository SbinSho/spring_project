package com.goCamping.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goCamping.dao.BoardReplyDAO;
import com.goCamping.domain.BoardReplyVO;
import com.goCamping.dto.BoardReplyDeleteDTO;
import com.goCamping.dto.BoardReplyEditDTO;
import com.goCamping.dto.BoardReplyWriteDTO;

@Service
public class BoardReplyServiceImpl implements BoardReplyService {

	@Autowired
	private BoardReplyDAO brDAO;
	
	// 댓글 갯수 조회
	@Override
	public int boardReply_count(int bno) {
		
		try {
			return brDAO.boardReply_count(bno);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	// 댓글 조회
	@Override
	public List<BoardReplyVO> boardReply_list(Map<String, Object> map) {
		
		try {
			return brDAO.boardReply_list(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// 댓글 작성
	@Override
	public Boolean reply_write(BoardReplyWriteDTO boardReplyWriteDTO) {
		
		try {
			return brDAO.reply_write(boardReplyWriteDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	// 댓글 작성
	@Override
	public Boolean reply_edit(BoardReplyEditDTO boardReplyEditDTO) {
		
		try {
			return brDAO.reply_edit(boardReplyEditDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	// 댓글 삭제
	@Override
	public Boolean reply_delete(BoardReplyDeleteDTO boardReplyDeleteDTO) {
		
		try {
			return brDAO.reply_delete(boardReplyDeleteDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
