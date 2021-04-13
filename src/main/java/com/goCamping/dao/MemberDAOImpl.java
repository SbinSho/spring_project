package com.goCamping.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.goCamping.domain.MemberVO;
import com.goCamping.dto.MemberChIdDTO;
import com.goCamping.dto.MemberChPassDTO;
import com.goCamping.dto.MemberLoginDTO;

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
	public Boolean member_create(MemberVO memberVO) {
		
		try {
			if(session.insert(NAMESPACE + ".member_create", memberVO) == 1) {
				return true;
			} 
			else {
				return false;
			}
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 로그인 처리
	@Override
	public MemberVO member_login(MemberLoginDTO MemberLoginDTO) throws Exception {
		return session.selectOne(NAMESPACE + ".member_login", MemberLoginDTO);
	}
	
	// 회원정보 불러오기
	@Override
	public HashMap<String, Object> member_select(String user_id) {
		return session.selectOne(NAMESPACE + ".member_select", user_id);
	}
	// 회원 아이디 수정
	@Override
	public Boolean member_chid(MemberChIdDTO MemberChIdDTO) {
		
		int result = session.update(NAMESPACE + ".member_chid", MemberChIdDTO);
			
		if( result == 1) {
			return true;
		}
		
		return false;
	}
	// 회원 비밀번호 확인
	@Override
	public String member_chpassCheck(String user_id) {
		
		return session.selectOne(NAMESPACE + ".member_chpassCheck", user_id);
	}
	
	
	// 회원 비밀번호 수정 
	@Override
	public Boolean member_chpass(MemberChPassDTO memberChPassDTO) {
		
		if( session.update(NAMESPACE + ".member_chpass", memberChPassDTO) == 1) {
			return true;
		}
		
		return false;
	}
	
	
	
}
