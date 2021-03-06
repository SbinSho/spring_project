package com.goCamping.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.goCamping.util.AuthInfo;

// 게시판 쓰기, 수정, 삭제 시 접근 권한을 체크하는 Interceptor
public class BoardAuthInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LogManager.getLogger(BoardAuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.info("BoardAuthInterceptor preHandle 진입");
		
		// 현재 세션을 가져옴
		HttpSession session = request.getSession(false);
		
		// PrintWriter 객체 생성 전 인코딩 설정
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pre_out;
		
		// contextPath 가져오기
		String ContextPath = request.getContextPath() != "" ? request.getContextPath() : "/";
		
		if(session == null) {
			logger.info("session이 존재하지 않음");
			pre_out = response.getWriter();
			pre_out.println("<script>alert('세션이 만료 되었습니다!'); location.href='" + ContextPath + "';</script>");
			pre_out.flush();
			pre_out.close();
			return false;
		}
		
		// 세션에 저장되어있는 객체
		AuthInfo authInfo = (AuthInfo) session.getAttribute("loginUser");
		// 객체에 저장되어 있는 아이디
		String session_userID = authInfo != null ? authInfo.getId() : null;
		
		// 현재 세션에 저장되어있는 loginUser가 존재하지 않으면
		// 로그인이 되어있지 않는 상태임으로 메인 홈페이지로 이동
		if( session_userID == null) {
			
			// 스프링 시큐리티의해 생성된 Athentication 객체 초기화 
			session.invalidate();
			pre_out = response.getWriter();
			pre_out.println("<script>alert('잘못된 접근입니다.'); location.href='" + ContextPath + "';</script>");
			pre_out.flush();
			pre_out.close();
			return false;
		} 
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		logger.info("BoardAuthInterceptor postHandle 진입");
		
	}
	
}
