package com.goCamping.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.goCamping.util.MessageUtils;

// spring security에서 자체적 으로 예외가 발생하게 되면 세션에 key가 SPRING_SECURITY_LAST_EXCEPTION인
// 예외 메시지를 저장하게 되는데 세션을 이용해서 계속해서 저장하게되면 서버에 부담이 가서 직접 예외처리 하기 위해 핸들러 사용
public class LoginFailureHandler implements AuthenticationFailureHandler{

	private String userID;
	private String defaultFailureUrl;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException auException)
			throws IOException, ServletException {

		String user_id = request.getParameter(userID);
		String errormsg = null;
		
		// 인증요구가 거부 되었을때 예외 발생
		if(auException instanceof AuthenticationServiceException) {
            errormsg = MessageUtils.getMessage("error.Authentication");
        } 
		// 비밀번호가 일치하지 않을 때 던지는 예외
		else if(auException instanceof BadCredentialsException) {
            errormsg = MessageUtils.getMessage("error.BadCredentials");
        } 
		// 존재하지 않는 아이디일때 던지는 예외
		else if(auException instanceof InternalAuthenticationServiceException) {
            errormsg = MessageUtils.getMessage("error.BadCredentials");
        } 
		// 인증 거부 - 계정 비활성화
		else if(auException instanceof DisabledException) {
            errormsg = MessageUtils.getMessage("error.Disaled");
        } 
		// 인증 거부 - 비밀번호 유효기간 만료
		else if(auException instanceof CredentialsExpiredException) {
            errormsg = MessageUtils.getMessage("error.CredentialsExpired");
        }
		
		request.setAttribute("user_id", user_id);
		request.setAttribute("errormsg", errormsg);
		
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);

	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

}
