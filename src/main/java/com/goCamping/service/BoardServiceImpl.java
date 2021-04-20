package com.goCamping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goCamping.dao.BoardDAO;
import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;

@Service
public class BoardServiceImpl  implements BoardService{

	
	@Autowired
	private BoardDAO bDao;
	
	// 총 게시글 수 조회
	@Override
	public int BoardCount() {
		return bDao.BoardCount();
	}

	// 게시글 리스트 조회
	@Override
	public List<BoardVO> board_list(Criteria cri) {
		return bDao.board_list(cri);
	}
	// 게시글 조회
	@Override
	public BoardVO board_read(int bno) {
		
		if(bDao.board_view(bno)) {
			return bDao.board_read(bno);
			
		}
		
		return null;
	}

	
	
}
