package com.goCamping.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goCamping.domain.BoardReplyVO;
import com.goCamping.dto.BoardReplyEditDTO;
import com.goCamping.dto.BoardReplyWriteDTO;

@Repository
public class BoardReplyDAOImpl implements BoardReplyDAO {

	private static final String NAMESPAE = "com.goCamping.mapper.boardReplyMapper";

	@Autowired
	private SqlSessionTemplate session;

	// 전체 댓글 갯수 조회
	@Override
	public int boardReply_count(int bno) throws Exception {
		return session.selectOne(NAMESPAE + ".boardReply_count", bno);
	}

	// 댓글 조회
	@Override
	public List<BoardReplyVO> boardReply_list(Map<String, Object> map) throws Exception {
		return session.selectList(NAMESPAE + ".boardReply_list", map);
	}

	// 댓글 작성
	@Override
	public Boolean reply_write(BoardReplyWriteDTO boardReplyWriteDTO) throws Exception {

		
		if (session.insert(NAMESPAE + ".reply_write", boardReplyWriteDTO) == 1) {
			return true;
		}

		return false;

	}
	
	// 댓글 수정
	@Override
	public Boolean reply_edit(BoardReplyEditDTO boardReplyEditDTO) throws Exception {
		
		if (session.insert(NAMESPAE + ".reply_edit", boardReplyEditDTO) == 1) {
			return true;
		}
		
		return false;
		
	}
	
	// 댓글 삭제
	@Override
	public Boolean reply_delete(BoardReplyEditDTO boardReplyEditDTO) throws Exception {
		
		if (session.insert(NAMESPAE + ".reply_delete", boardReplyEditDTO) == 1) {
			return true;
		}
		
		return false;
		
	}

}
