package com.goCamping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goCamping.dao.MemberDAO;
import com.goCamping.domain.MemberVO;

@Service
public class MeberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO mdao;

	// 아이디 중복확인
	@Override
	public int id_Check(String user_id) throws Exception {
		return mdao.id_Check(user_id);
	}

	// 닉네임 중복확인
	@Override
	public int nick_Check(String user_nickname) throws Exception {
		return mdao.nick_Check(user_nickname);
	}

	// 회원가입 처리
	@Override
	public int member_join(MemberVO memberVO) throws Exception {
		return mdao.member_join(memberVO);
	}


	
	
	
	
}
