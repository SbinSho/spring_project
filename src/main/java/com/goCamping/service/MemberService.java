package com.goCamping.service;

import java.util.HashMap;
import java.util.Map;

import com.goCamping.dto.MemberJoinDTO;
import com.goCamping.dto.MemberLoginDTO;

public interface MemberService {

	// 아이디 중복확인
	public int id_Check(String user_id) throws Exception;
	
	// 닉네임 중복확인
	public int nick_Check(String user_nickname) throws Exception;
	
	// 회원가입 처리
	public Boolean member_create(MemberJoinDTO memberJoinDTO) throws Exception;

	// 로그인 처리
	public boolean member_login(MemberLoginDTO encryptLoginDTO) throws Exception;
	
	// 회원정보 불러오기
	public HashMap<String, Object> member_select(String user_id);
}
