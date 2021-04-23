package com.goCamping.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class BoardWriteDTO {

	@NotBlank
	@Size(min = 1, max = 30)
	private String 	title;
	@NotBlank
	@Pattern(regexp = "^[a-z0-9][a-z0-9_\\\\-]{4,19}$")
	private String	writer;
	@NotBlank
	@Size(min = 1, max = 1000)
	private String	content;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
	
}
