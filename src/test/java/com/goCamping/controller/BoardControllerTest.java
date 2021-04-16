package com.goCamping.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
// 프로젝트의 web.xml이 아닌 가상의 web.xml을 사용
@WebAppConfiguration
// 어플리케이션 설정과, 디스패처 서블릿의 xml설정
@ContextConfiguration(
		locations = {
				"file:src/main/webapp/WEB-INF/spring/*.xml",
				"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"}
		)
public class BoardControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mock;
	
	@Before
	public void setUp() {
		mock = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void 게시글_조회되다() {
		
		try {
			this.mock.perform(get("/board/list"))
			.andDo(print())
			.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
