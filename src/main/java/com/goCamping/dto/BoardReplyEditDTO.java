package com.goCamping.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class BoardReplyEditDTO {
	
	private int rno;
	@NotBlank
	@Pattern(regexp = "^[a-z0-9][a-z0-9_\\\\-]{4,19}$")
	private String writer;
	@NotBlank
	@Size(min = 1, max = 300)
	private String content;

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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "BoardReplyEditDTO [rno=" + rno + ", writer=" + writer + ", content=" + content + "]";
	}

	
	

}
