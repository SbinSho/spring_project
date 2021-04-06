package com.goCamping.service;

import com.goCamping.dto.MemberLoginDTO;
import com.goCamping.dto.MemberJoinDTO;

public interface MemberService {

	// 아이디 중복확인
	public int id_Check(String user_id) throws Exception;
	
	// 닉네임 중복확인
	public int nick_Check(String user_nickname) throws Exception;
	
	// 회원가입 처리
	public Boolean member_create(MemberJoinDTO memberJoinDTO) throws Exception;

	// 로그인 처리
	public boolean member_login(MemberLoginDTO encryptLoginDTO) throws Exception;
}
