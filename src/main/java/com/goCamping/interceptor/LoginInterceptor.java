package com.goCamping.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.goCamping.dto.MemberLoginDTO;
import com.goCamping.util.AuthInfo;

// 사용자가 로그인 할떄만 사용하는 인터셉터
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LogManager.getLogger(LoginInterceptor.class);

	// preHandle() : 컨트롤러 보다 먼저 수행되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		logger.info("LoginInterceptor preHandle 진입");

		// 현재 세션을 가져옴
		HttpSession session = request.getSession();

		// 세션에 저장된 유저가 없을 시에만 진입허용
		if (session.getAttribute("loginUser") == null) {
			return true;
		}
		
		// contextPath 가져오기
		String ContextPath = request.getContextPath() != "" ? request.getContextPath() : "/";

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('잘못된 접근입니다.'); location.href='" + ContextPath + "';</script>");
		out.flush();
		out.close();
		return false;

	}

	// postHandle() : 컨트롤러가 수행되고 화면이 보여지기 직전에 수행되는 메소드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		logger.info("LoginInterceptor postHandle 진입 ");
		
		// 현재 요청 방식이 GET 방식이면 메소드 종료
		if (request.getMethod().equals("GET")) {
			return;
		}

		// 현재 요청의 세션 가져오기
		// Controller 에서 사용된 데이터 전부 ModelMap에 담음
		HttpSession session = request.getSession();

		ModelMap map = modelAndView.getModelMap();

		// PrintWriter 객체 생성 전 인코딩 설정
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		MemberLoginDTO memberLoginDTO = (MemberLoginDTO) map.get("memberLoginDTO");

		// contextPath 가져오기
		String ContextPath = request.getContextPath() != "" ? request.getContextPath() : "/";
		
		// 개인키가 존재하지 않을 경우
		if (map.get("error") != null) {

			logger.info("private 진입");

			map.remove("error");

			out.println("<script>alert('오류 발생!'); location.href='" + ContextPath + "/member/login';</script>");
			out.flush();
		}
		// 객체 유효성 검사 실패 또는 DB에 매칭되는 아이디와 비밀번호가 같지 않을 경우
		else if (map.get("check") != null) {

			logger.info("check 진입");

			map.remove("check");

			out.println("<script>alert('아이디 또는 비밀번호를 확인해주세요!'); history.back();</script>");
			out.flush();
		} else {

			// 세션에 저장할 객체 사용
			AuthInfo auth = new AuthInfo(memberLoginDTO);

			// 세션에 현재 사용자 정보 저장
			session.setAttribute("loginUser", auth);

			// 현재 세션에 저장된 아이디의 cookie를 value로 하는 cookie 생성
			Cookie loginCookie = new Cookie("loginCookie", auth.getId());
			// cookie에 현재 도메인값 입력
			loginCookie.setPath("/");

			// 사용자가 아이디 기억 버튼 체크했을 시에 쿠키가 일주일 동안 유지되게 저장
			if (memberLoginDTO.getUser_reId().equals("true")) {
				loginCookie.setMaxAge(7 * 24 * 60 * 60);
			} else { // 버튼 체크 안했을시에는 0으로 쿠키를 바로 지금 소멸되게 만듬
				loginCookie.setMaxAge(0);
			}

			// 브라우저에 작성한 cookie를 응답함
			// cookie응답후 메인페이지로 이동
			response.addCookie(loginCookie);
			out.println("<script>alert('로그인 완료!'); location.href='"+ ContextPath + "';</script>");
			out.flush();
		}

		out.close();
	}

}
