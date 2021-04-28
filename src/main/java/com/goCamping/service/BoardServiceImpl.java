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
	@Override
	public BoardVO board_read(int bno) {

		if (bDao.board_view(bno)) {
			return bDao.board_read(bno);

		}

		return null;
	}

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

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return result;
	}

	@Override
	public Boolean board_edit(BoardEditDTO boardEditDTO) {
		return bDao.board_edit(boardEditDTO);
	}
	
	@Override
	public Boolean board_delete(HashMap<String, Object> del) {
		return bDao.board_delete(del);
	}
	// 업로드 파일 조회
	@Override
	public List<Map<String, Object>> board_fileList(int bno) throws Exception {
		return bDao.board_fileList(bno);
	}

	
}
