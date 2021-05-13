package com.goCamping.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/* Spring Security 로그인을 위한 UserDetails VO 객체 */
public class UserDetailsVO implements UserDetails{
	
	private static final long seriaVersionUID = 1L;
	
	private String username; // ID
	private String password; // PW
	private List<GrantedAuthority> authorities;

	
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// 한 사람이 여러 권한을 가질 수 있기에
	// List<String> 타입으로 권한 데이터를 받아
	// GrantedAuthority 인터페이스를 구현한 
	// SimleGrantedAuthority 클래스에 하나씩 담아준다.
	public void setAuthorities(List<String> authList) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(int i = 0; i < authList.size(); i++) {
			authorities.add(new SimpleGrantedAuthority(authList.get(i)));
		}
		
		this.authorities = authorities;
	}

	
	// 권한
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stubd
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() { // 계정이 만료되지 '않았는가'?
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() { // 계정이 잠기지 '않았는가'?
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() { // 패스워드가 잠기지 '않았는가'?
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() { // 계정이 사용 '가능한가'? ( 활성화 되었는가? )
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
