package com.goCamping.dao;

import java.util.List;

import com.goCamping.domain.BoardVO;
import com.goCamping.domain.Criteria;

public interface BoardDAO {

	public int BoardCount();

	public List<BoardVO> board_list(Criteria cri);

}
