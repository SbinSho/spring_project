package com.goCamping.dto;

public class BoardReplyEditDTO {
	
	private int rno;
	private String writer;
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
