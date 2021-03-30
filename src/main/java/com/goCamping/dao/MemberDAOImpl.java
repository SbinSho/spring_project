package com.goCamping.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goCamping.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	
	private static final String NAMESPACE = "com.goCamping.mapper.memberMapper";
	
	@Autowired
	private SqlSessionTemplate session;

	// 아이디 중복확인
	@Override
	public int id_Check(String user_id) throws Exception {
		return session.selectOne(NAMESPACE + ".id_Check", user_id);
	}

	// 닉네임 중복 확인
	@Override
	public int nick_Check(String user_nickname) throws Exception {
		return session.selectOne(NAMESPACE + ".nick_Check", user_nickname);
	}

	// 메일 중복 확인
	@Override
	public int mail_Check(String user_mail) throws Exception {
		return session.selectOne(NAMESPACE + ".mail_Check", user_mail);
	}

	// 회원가입 처리
	@Override
	public int member_join(MemberVO memberVO) throws Exception {
		return session.insert(NAMESPACE + ".member_join", memberVO);
	}
	
	
	
	
	
}
