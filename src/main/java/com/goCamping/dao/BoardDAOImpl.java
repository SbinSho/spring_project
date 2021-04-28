package com.goCamping.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;
import com.goCamping.dto.BoardEditDTO;
import com.goCamping.dto.BoardWriteDTO;

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
	// 게시글 작성
	@Override
	public Boolean board_write(BoardWriteDTO boardWriteDTO) {
		
		try {
			if(session.insert(NAMESPACE + ".board_write", boardWriteDTO) == 1) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	// 게시글 작성 후 마지막 번호 가져오기
	@Override
	public int board_getLastBno() {
		return session.selectOne(NAMESPACE + ".board_getLastBno");
	}
	// 파일 업로드
	@Override
	public Boolean board_fileUpload(Map<String, Object> map) {
		
		if(session.insert(NAMESPACE + ".board_fileUpload", map) == 1) {
			return true;
		}
		
		return false;
	}
	// 업로드 파일 조회
	@Override
	public List<Map<String, Object>> board_fileList(int bno) throws Exception{
		return session.selectList(NAMESPACE + ".board_fileList", bno);
	}
	// 게시글 수정
	@Override
	public Boolean board_edit(BoardEditDTO boardEditDTO) {
		
		if(session.update(NAMESPACE + ".board_edit", boardEditDTO) == 1) {
			return true;
		}
		
		return false;
	}
	// 게시글 삭제
	@Override
	public Boolean board_delete(HashMap<String, Object> del) {
		
		if(session.update(NAMESPACE + ".board_delete", del) == 1) {
			return true;
		}
		
		return false;
	}
	

}
