package com.goCamping.util;

import com.goCamping.dto.MemberLoginDTO;

public class AuthInfo {

	private String id;

	public AuthInfo() {}
	
	public AuthInfo(MemberLoginDTO MemberLoginDTO) {
		this.id = MemberLoginDTO.getUser_id();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	
}
