package com.goCamping.domain;

import java.sql.Timestamp;

public class UnregisteredVO {

	private String 	user_id;
	private String 	unregistered_title;
	private String	unregistered_content;
	private String	unregistered_filename;
	private Boolean unregistered_public;
	private Timestamp unregistered_regdate;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUnregistered_title() {
		return unregistered_title;
	}
	public void setUnregistered_title(String unregistered_title) {
		this.unregistered_title = unregistered_title;
	}
	public String getUnregistered_content() {
		return unregistered_content;
	}
	public void setUnregistered_content(String unregistered_content) {
		this.unregistered_content = unregistered_content;
	}
	public String getUnregistered_filename() {
		return unregistered_filename;
	}
	public void setUnregistered_filename(String unregistered_filename) {
		this.unregistered_filename = unregistered_filename;
	}
	public Boolean getUnregistered_public() {
		return unregistered_public;
	}
	public void setUnregistered_public(Boolean unregistered_public) {
		this.unregistered_public = unregistered_public;
	}
	public Timestamp getUnregistered_regdate() {
		return unregistered_regdate;
	}
	public void setUnregistered_regdate(Timestamp unregistered_regdate) {
		this.unregistered_regdate = unregistered_regdate;
	}
	
	@Override
	public String toString() {
		return "UnregisteredVO [user_id=" + user_id + ", unregistered_title=" + unregistered_title
				+ ", unregistered_content=" + unregistered_content + ", unregistered_filename=" + unregistered_filename
				+ ", unregistered_public=" + unregistered_public + ", unregistered_regdate=" + unregistered_regdate
				+ "]";
	}
	
	
}
