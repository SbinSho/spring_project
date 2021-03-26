package com.goCamping.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goCamping.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	
	private static final String NAMESPACE = "com.goCamping.mapper.memberMapper";
	
	@Autowired
	private SqlSessionTemplate session;
	
	@Override
	public List<MemberVO> member_list() throws Exception {
		return session.selectList(NAMESPACE+".member_list");
	}

	
	
	
}
