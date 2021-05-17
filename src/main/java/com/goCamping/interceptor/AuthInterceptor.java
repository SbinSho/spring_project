package com.goCamping.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.goCamping.util.AuthInfo;

// 로그인이 되어있지 않으면 접근할수 없는 부분을 위한 인터셉터 
public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LogManager.getLogger(AuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.info("AuthInterceptor preHandle 진입");
		
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
		// 요청 받은 아이디
		String request_userID = request.getParameter("user_id") != null ? request.getParameter("user_id") : null;

		
		// 현재 세션에 저장되어있는 loginUser가 존재하지 않으면
		// 로그인이 되어있지 않는 상태임으로 메인 홈페이지로 이동
		if( session_userID == null || request_userID == null || !(request_userID.equals(session_userID))) {
			
			// 스프링 시큐리티의해 생성된 Athentication 객체 초기화
			// 현재 프로젝트 초기에는 스프링 시큐리티가 적용 되지 않은 상태에서
			// 모든 작업 처리를 하였는데, 스프링 시큐리티 적용 후 모든 로직을 변경하기 힘들어
			// 스프링 시큐리티 로그인 성공시 Athentication 객체 생성 및 세션에 현재 계정의 아이디 까지 올려둔 상태에다 ( 매우 비효율적인 상태 하지만 실습 목적의 프로젝트 임으로 진행함 )
			// 그래서 권한 체크시 세션 전부를 날려 Athentication 및 세션을 초기화함
			session.invalidate();
			pre_out = response.getWriter();
			pre_out.println("<script>alert('잘못된 접근입니다.'); location.href='" + ContextPath + "';</script>");
			pre_out.flush();
			pre_out.close();
			return false;
		} 
		
		return true;
	}
}
