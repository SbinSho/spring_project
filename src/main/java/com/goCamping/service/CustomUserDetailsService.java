package com.goCamping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.goCamping.dao.MemberDAO;
import com.goCamping.domain.CustomUserDetails;

// DB에서 유저 정보를 직접 가져오는 인터페이스 구현체
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberDAO memberDAO;

	@Override
	public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		CustomUserDetails user = memberDAO.getUserById(user_id);
		
		return user;
	}
	
	
	
}
