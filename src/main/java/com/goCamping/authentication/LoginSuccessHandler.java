package com.goCamping.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.goCamping.util.AuthInfo;

public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private String user_id;
	private String defaultUrl;
	
	// spring에서 제공하는 사용자의 요청을 저장하고 꺼낼 수 있는 인터페이스
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		
		AuthInfo authInfo = new AuthInfo();
		authInfo.setId(request.getParameter(user_id));
		session.setAttribute("loginUser", authInfo);

		// 현재 세션에 저장된 아이디의 cookie를 value로 하는 cookie 생성
		Cookie loginCookie = new Cookie("loginCookie", authInfo.getId());
		// cookie에 현재 도메인값 입력
		loginCookie.setPath("/");

		// 사용자가 아이디 기억 버튼 체크했을 시에 쿠키가 일주일 동안 유지되게 저장
		if (request.getParameter("user_reId").equals("true")) {
			loginCookie.setMaxAge(7 * 24 * 60 * 60);
		} else { // 버튼 체크 안했을시에는 0으로 쿠키를 바로 지금 소멸되게 만듬
			loginCookie.setMaxAge(0);
		}
		
		// 브라우저에 작성한 cookie를 응답함
		response.addCookie(loginCookie);
		// 로그인 하는 과정에서 생긴 세션에 저장된 메시지를 삭제
		clearAuthenticationAttributes(request);
		// 어느 URL로 보낼지 판단하기 위한 메소드
		resultRedirectStrategy(request, response, authentication);
	}
	
    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
    	
        // RequestCache를 이용해 사용자 요청 정보들이 들어있는 SavedRequest 클래스 생성
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        // 세션에 이동할 url의 정보가 담겨져 있는지 판단( 접근 권한이 없는 URL에 요청 한 경우, 직접 로그인 페이지 접속시 생성 안됨 )
        if(savedRequest!=null) {
        	// 존재하면 요청한 URL로 이동
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStratgy.sendRedirect(request, response, targetUrl);
            
        } else {
        	// 존재하지 않으면 홈 화면으로 이동
            redirectStratgy.sendRedirect(request, response, defaultUrl);
            
        }
        
    }
    
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        
    	HttpSession session = request.getSession(false);
    	
        if(session==null) return;
        // 로그인 실패 이후 로그인 성공시 세션에 저장되어있는 에러 메시지 삭제
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        
    }

	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getDefaultUrl() {
		return defaultUrl;
	}


	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	
	
	
}
