package com.goCamping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goCamping.dao.BoardReplyDAO;
import com.goCamping.domain.BoardReplyVO;

@Service
public class BoardReplyServiceImpl implements BoardReplyService {

	@Autowired
	private BoardReplyDAO brDAO;
	
	
	@Override
	public int boardReply_count() {
		return brDAO.boardReply_count();
	}

	@Override
	public List<BoardReplyVO> boardReply_list(int bno) {
		return brDAO.boardReply_list(bno);
	}

}
