package com.goCamping.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 로그인이 되어있는 경우 사용되지 않는 경로를 위한 인터셉터
public class NotLoginInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LogManager.getLogger(NotLoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.info("NotLoginInterceptor preHandle 진입");
		
		// 현재 세션을 가져옴
		HttpSession session = request.getSession();
		
		if( session.getAttribute("loginUser") == null) {
			return true;
		}

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('잘못된 접근입니다.'); location.href='" + request.getContextPath() +"/';</script>");
		out.flush();
		out.close();
		return false;
		
	}
}
