package com.goCamping.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PasswordService {

	
	public void mainLogout(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
