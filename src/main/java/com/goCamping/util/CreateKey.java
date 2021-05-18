package com.goCamping.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CreateKey {
	
	public static void set(HttpServletRequest request, Map<String, Object> map) {
		
		HttpSession session = request.getSession();
		session.setAttribute("_RSA_WEB_KEY_", map.get("_RSA_WEB_KEY_"));
		request.setAttribute("RSAModulus", map.get("RSAModulus"));
		request.setAttribute("RSAExponent", map.get("RSAExponent"));
		
		// 세션 유지시간 1800초 설정
		session.setMaxInactiveInterval(1800);
		
	}

}
