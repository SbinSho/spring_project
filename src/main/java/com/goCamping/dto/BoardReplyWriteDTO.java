package com.goCamping.dto;

public class BoardReplyWriteDTO {
	
	private int bno;
	private String writer;
	private String content;
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
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
		return "BoardReplyWriteDTO [bno=" + bno + ", writer=" + writer + ", content=" + content + "]";
	}
	
	

}
