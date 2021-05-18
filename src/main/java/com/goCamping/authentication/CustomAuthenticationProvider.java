package com.goCamping.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.goCamping.domain.CustomUserDetails;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService usDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// Authentication 객체는 사용자가 입력한 로그인 정보를 담고있다. ( 클라이언트에서 넘어온 로그인 정보 )
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		System.out.println("CustomAuthenticationProvider 실행!" );
		
		
		/* 사용자가 입력한 정보 */
		String user_id = (String) authentication.getPrincipal();
		String user_pwd = (String) authentication.getCredentials();
		
		/* DB에서 가져온 정보 ( 커스터마이징 가능 ) */
		CustomUserDetails user = (CustomUserDetails) usDetailsService.loadUserByUsername(user_id);
		
		/* 인증 진행 */
		// DB에 정보가 없는 경우 예외 발생
		// 입력된 ID 및 PW가 맞지 않을 경우
		if(user == null) {
			throw new AuthenticationServiceException(user_id);
		} else if (!user_id.equals(user.getUsername()) || !passwordEncoder.matches(user_pwd, user.getPassword())) {
			throw new BadCredentialsException(user_id);
		}
		
		if(!user.isEnabled()) {
			throw new BadCredentialsException(user_id);
		}
		
		// 객체를 계속 사용해야 하므로 패스워드 정보 삭제
		user.setUser_pwd(null);
		
		/* 최종 리턴 시킬 새로만든 Authentication 객체 */
		Authentication newAuth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		return newAuth;
	}

	@Override
	// 위의 authenticate 메소드에서 반환한 객체가 유효한 타입이 맞는지 검사
	// null 값이거나 잘못된 타입을 반환했을 경우 인증 실패로 간주
	public boolean supports(Class<?> authentication) {
		// 스프링 Security가 요구하는 UsernamePasswordAuthenticationToken 타입이 맞는지 확인
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	
}
