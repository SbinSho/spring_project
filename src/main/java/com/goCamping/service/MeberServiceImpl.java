package com.goCamping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goCamping.dao.MemberDAO;
import com.goCamping.domain.MemberVO;

@Service
public class MeberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO mdao;
	
	@Override
	public List<MemberVO> member_list() throws Exception {
		return mdao.member_list();
	}

	
	
}
