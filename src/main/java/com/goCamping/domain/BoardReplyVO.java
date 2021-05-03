package com.goCamping.domain;

import java.sql.Timestamp;

public class BoardReplyVO {
	
	private int bno;
	private int rno;
	private String content;
	private String writer;
	private Timestamp regdate;
	public int getBno() {
		return bno;
	}
	public int getRno() {
		return rno;
	}
	public String getContent() {
		return content;
	}
	public String getWriter() {
		return writer;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	@Override
	public String toString() {
		return "BoardReplyVO [bno=" + bno + ", rno=" + rno + ", content=" + content + ", writer=" + writer
				+ ", regdate=" + regdate + "]";
	}
	
	

}
