package com.goCamping.domain;

import java.sql.Timestamp;

public class BoardReplyVO {
	
	private int bno;
	private int rno;
	private String content;
	private String writer;
	private Timestamp regdate;
	private Timestamp last_update;

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
	public Timestamp getLast_update() {
		return last_update;
	}
	public void setLast_update(Timestamp last_update) {
		this.last_update = last_update;
	}
	@Override
	public String toString() {
		return "BoardReplyVO [bno=" + bno + ", rno=" + rno + ", content=" + content + ", writer=" + writer
				+ ", regdate=" + regdate + ", last_update=" + last_update + "]";
	}
	
	
}
