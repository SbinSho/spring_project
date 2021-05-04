package com.goCamping.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goCamping.domain.BoardReplyVO;

@Repository
public class BoardReplyDAOImpl implements BoardReplyDAO {

	private static final String NAMESPAE = "com.goCamping.mapper.boardReplyMapper";
	
	@Autowired
	private SqlSessionTemplate session;
	
	@Override
	public int boardReply_count() {
		return session.selectOne(NAMESPAE + ".boardReply_count");
	}

	@Override
	public List<BoardReplyVO> boardReply_list(int bno) {
		return session.selectList(NAMESPAE + ".boardReply_list", bno);
	}

}
