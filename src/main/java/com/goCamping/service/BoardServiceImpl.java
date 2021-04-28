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

		if (bDao.board_view(bno)) {
			return bDao.board_read(bno);

		}

		return null;
	}
	// 게시글 작성
	@Transactional
	@Override
	public Boolean board_write(BoardWriteDTO boardWriteDTO, MultipartHttpServletRequest multipartHttpServletRequest) {

		Boolean result = false;

		result = bDao.board_write(boardWriteDTO);

		int bno = bDao.board_getLastBno();
		
		try {
			List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(multipartHttpServletRequest);

			for (Map<String, Object> map : list) {
				map.put("bno", bno);
				bDao.board_fileUpload(map);
			}

			result = true;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	// 게시글 수정
	@Override
	public Boolean board_edit(BoardEditDTO boardEditDTO, String[] files, String[] fileNames, MultipartHttpServletRequest multipartHttpServletRequest) {
		
		try {
			bDao.board_edit(boardEditDTO);
			
			List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(multipartHttpServletRequest);
			Map<String, Object> tempMap = null;
			
			for (Map<String, Object> map : list) {
				
				tempMap = map;
				
				if(tempMap.get("IS_NEW").equals('y')) {
					bDao.board_fileUpload(tempMap);
				} else {
					bDao.board_editFile(tempMap);
				}
			}
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// 게시글 삭제
	@Override
	public Boolean board_delete(HashMap<String, Object> del) {
		return bDao.board_delete(del);
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
	
	// 업로드 파일 수정
	@Override
	public void board_editFile(Map<String, Object> map) throws Exception {
		bDao.board_editFile(map);
	}
	
	
	
}
