package com.goCamping.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Setup With StandAlone ( 컨트롤러 단독 테스트 )
public class  HomeControllerTest{

	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
	}
	
	@Test
	public void 홈화면으로_이동한다() throws Exception{
		this.mockMvc.perform(get("/"))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	
}
