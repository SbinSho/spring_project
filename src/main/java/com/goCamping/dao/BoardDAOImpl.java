package com.goCamping.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	private static final String NAMESPACE = "com.goCamping.mapper.boardMapper";

	@Autowired
	private SqlSessionTemplate session;
	
	// 총 게시글 조회
	@Override
	public int BoardCount() {
		return session.selectOne(NAMESPACE + ".board_count");
	}
	// 게시글 리스트 조회
	@Override
	public List<BoardVO> board_list(Criteria cri) {
		return session.selectList(NAMESPACE + ".board_list", cri);
	}
	// 게시글 조회
	@Override
	public BoardVO board_read(int bno) {
		return session.selectOne(NAMESPACE + ".board_read", bno);
	}
	@Override
	public Boolean board_view(int bno) {
		
		if (session.update(NAMESPACE + ".board_view", bno) == 1) {
			return true;
		}
		return false;
	}
	
	
	

}
