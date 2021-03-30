package com.goCamping.service;

import com.goCamping.domain.MemberVO;

public interface MemberService {

	// 아이디 중복확인
	public int id_Check(String user_id) throws Exception;
	
	// 닉네임 중복확인
	public int nick_Check(String user_nickname) throws Exception;
	
	// 회원가입 처리
	public int member_join(MemberVO memberVO) throws Exception;
	
}
