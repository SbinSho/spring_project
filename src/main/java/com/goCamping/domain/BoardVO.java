package com.goCamping.domain;

import java.sql.Timestamp;

public class BoardVO {

	private int bno;
	private String 	title;
	private String	writer;
	private String	content;
	private int view_cnt;
	private String del_chk;
	private Timestamp regdate;
	
	
	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

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

	public int getView_cnt() {
		return view_cnt;
	}

	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
	}

	public String getDel_chk() {
		return del_chk;
	}

	public void setDel_chk(String del_chk) {
		this.del_chk = del_chk;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", title=" + title + ", writer=" + writer + ", content=" + content
				+ ", view_cnt=" + view_cnt + ", del_chk=" + del_chk + ", regdate=" + regdate + "]";
	}
	
	
}
