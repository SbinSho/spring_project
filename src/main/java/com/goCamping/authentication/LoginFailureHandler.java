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

public class LoginFailureHandler implements AuthenticationFailureHandler{

	private String userID;
	private String defaultFailureUrl;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException auException)
			throws IOException, ServletException {

		String user_id = request.getParameter(userID);
		String errormsg = null;
		
		if(auException instanceof AuthenticationServiceException) {
            errormsg = MessageUtils.getMessage("error.Authentication");
        } else if(auException instanceof BadCredentialsException) {
            errormsg = MessageUtils.getMessage("error.BadCredentials");
        } else if(auException instanceof InternalAuthenticationServiceException) {
            errormsg = MessageUtils.getMessage("error.BadCredentials");
        } else if(auException instanceof DisabledException) {
            errormsg = MessageUtils.getMessage("error.Disaled");
        } else if(auException instanceof CredentialsExpiredException) {
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
