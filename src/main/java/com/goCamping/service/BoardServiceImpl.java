package com.goCamping.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.goCamping.dao.BoardDAO;
import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;
import com.goCamping.dto.BoardEditDTO;
import com.goCamping.dto.BoardWriteDTO;
import com.goCamping.util.FileUtils;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO bDao;

	// 첨부 파일 처리를 위한 bean 설정
	@Autowired
	private FileUtils fileUtils;

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
	@Transactional
	@Override
	public BoardVO board_read(int bno) {
		
		// 조회수 증가 및 게시판 글 조회하기
		if (bDao.board_viewCount(bno)) {
			return bDao.board_read(bno);
		}

		return null;
	}
	// 게시글 작성
	@Transactional
	@Override
	public Boolean board_write(BoardWriteDTO boardWriteDTO, MultipartHttpServletRequest multipartHttpServletRequest) {

		try {
			// 게시글 작성 처리
			bDao.board_write(boardWriteDTO);
			// 마지막으로 업데이트 된 게시판 번호 가져오기
			int bno = bDao.board_getLastBno();
		
			// 첨부 파일이 존재하는지 확인하고, 존재하면 list로 반환
			List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(multipartHttpServletRequest);
			
			// 추가 된 파일이 존재하는 경우 파일 업로드 처리
			for (Map<String, Object> map : list) {
				map.put("bno", bno);
				bDao.board_fileUpload(map);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	// 게시글 수정
	@Transactional
	@Override
	public Boolean board_edit(BoardEditDTO boardEditDTO, String[] del_files, MultipartHttpServletRequest multipartHttpServletRequest) {
		
		try {
			// 게시글 수정 처리
			bDao.board_edit(boardEditDTO);
			// 첨부 파일이 존재하는지 확인하고, 존재하면 list로 반환
			List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(multipartHttpServletRequest);
			
			// 게시글에 첨부된 파일이 삭제 되었는지 체크
			if(del_files.length > 0) {
				// 삭제 된 첨부 파일들을 DB에서 삭제 처리
				for (String file_no : del_files) {
					bDao.board_editFile(Integer.parseInt(file_no));
				}
			}
			
			// 첨부 파일이 추가 됬을 경우를 위해 게시판 번호 추가
			int bno = boardEditDTO.getBno();
			// 추가 된 파일이 존재하는 경우 파일 업로드 처리
			for (Map<String, Object> map : list) {
				map.put("bno", bno);
				bDao.board_fileUpload(map);
			}
			
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// 게시글 삭제
	@Transactional
	@Override
	public Boolean board_delete(HashMap<String, Object> del) {
		
		try {
			
			if(bDao.board_delete(del) && bDao.board_file_delete(del)) {
				return true;
			}
			
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	// 업로드 파일 조회
	@Override
	public List<Map<String, Object>> board_fileList(int bno) throws Exception {
		return bDao.board_fileList(bno);
	}
	

	// 업로드 파일 다운로드
	@Override
	public Map<String, Object> board_fileInfo(int file_no) throws Exception {
		return bDao.board_fileInfo(file_no);
	}
	
	
}
