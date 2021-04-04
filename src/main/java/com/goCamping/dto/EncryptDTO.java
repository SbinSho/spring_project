package com.goCamping.dto;

public class EncryptDTO {
	
	private String en_userID;
	private String en_userName;
	private String en_userNickName;
	private String en_userMail;
	private String en_userPwd;
	
	
	public String getEn_userID() {
		return en_userID;
	}

	public void setEn_userID(String en_userID) {
		this.en_userID = en_userID;
	}

	public String getEn_userName() {
		return en_userName;
	}

	public void setEn_userName(String en_userName) {
		this.en_userName = en_userName;
	}

	public String getEn_userNickName() {
		return en_userNickName;
	}

	public void setEn_userNickName(String en_userNickName) {
		this.en_userNickName = en_userNickName;
	}

	public String getEn_userMail() {
		return en_userMail;
	}

	public void setEn_userMail(String en_userMail) {
		this.en_userMail = en_userMail;
	}

	public String getEn_userPwd() {
		return en_userPwd;
	}

	public void setEn_userPwd(String en_userPwd) {
		this.en_userPwd = en_userPwd;
	}

	@Override
	public String toString() {
		return "EncryptDTO [en_userID=" + en_userID + ", en_userName=" + en_userName + ", en_userNickName="
				+ en_userNickName + ", en_userMail=" + en_userMail + ", en_userPwd=" + en_userPwd + "]";
	}
	
}
