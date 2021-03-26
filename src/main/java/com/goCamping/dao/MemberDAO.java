package com.goCamping.dao;

import java.util.List;

import com.goCamping.domain.MemberVO;

public interface MemberDAO {
	
	public List<MemberVO> member_list() throws Exception;

}
