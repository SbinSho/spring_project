package com.goCamping.dao;

import java.util.HashMap;

import org.springframework.dao.DuplicateKeyException;

import com.goCamping.domain.CustomUserDetails;
import com.goCamping.domain.MemberVO;
import com.goCamping.dto.MemberChIdDTO;
import com.goCamping.dto.MemberChPassDTO;
import com.goCamping.dto.MemberLoginDTO;

public interface MemberDAO {
	
	// 아이디 중복확인
	public int id_Check(String user_id) throws Exception;

	// 닉네임 중복확인
	public int nick_Check(String user_nickname) throws Exception;
	
	// 메일 중복확인
	public int mail_Check(String user_mail) throws Exception;
	
	// 회원가입 처리
	public Boolean member_create(MemberVO memberVO) throws DuplicateKeyException;

	// 로그인 처리
	public MemberVO member_login(MemberLoginDTO MemberLoginDTO) throws Exception;
	
	// 회원정보 불러오기
	public HashMap<String, Object> member_select(String user_id);

	// 아이디 수정
	public Boolean member_chid(MemberChIdDTO memberChIdDTO);
	
	// 비밀번호 확인
	public String member_chpassCheck(String user_id);
	
	// 비밀번호 수정
	public Boolean member_chpass(MemberChPassDTO memberChPassDTO);
	
	// 회원 탈퇴
	public Boolean member_delete(String user_id);
	
	// 스프링 시큐리티 작업 처리를 위한 메소드
	public CustomUserDetails getUserById(String user_id);
	
}
