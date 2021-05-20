package com.goCamping.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class BoardReplyDeleteDTO {
	
	@Min(value = 1)
	private int rno;
	@NotBlank
	@Pattern(regexp = "^[a-z0-9][a-z0-9_\\\\-]{4,19}$")
	private String writer;
	
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	
	

}
