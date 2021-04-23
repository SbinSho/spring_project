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
	public String getTitle() {
		return title;
	}
	public String getWriter() {
		return writer;
	}
	public String getContent() {
		return content;
	}
	public int getView_cnt() {
		return view_cnt;
	}
	public String getDel_chk() {
		return del_chk;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	
	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", title=" + title + ", writer=" + writer + ", content=" + content
				+ ", view_cnt=" + view_cnt + ", del_chk=" + del_chk + ", regdate=" + regdate + "]";
	}
	
	
	
}
