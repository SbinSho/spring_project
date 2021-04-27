package com.goCamping.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SmartEditorController {

	@RequestMapping("/smarteditor")
	public String smartEditor() {
		return "smartEditor";
	}
	
	@RequestMapping("/submit")
	public void submit(HttpServletRequest request) {
		System.out.println("에디터 컨텐츠값 : " + request.getParameter("editor"));
	}
	
}
