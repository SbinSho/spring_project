package com.goCamping.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/* Spring Security 로그인을 위한 UserDetails VO 객체 */
// 사용자의 정보를 담는 인터페이스의 UserDetails의 구현체 ( VO 역할을 함 )
public class CustomUserDetails implements UserDetails{
	
	
	private static final long serialVersionUID = 1L;
	
	private String user_id;
    private String user_pwd;
    private String AUTHORITY;
    private boolean ENABLED;
    private String user_name;
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority(AUTHORITY));
		
		return auth;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user_pwd;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user_id;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return ENABLED;
	}
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	
	
	
	
}
