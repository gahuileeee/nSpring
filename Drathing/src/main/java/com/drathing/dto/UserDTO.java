package com.drathing.dto;

public class UserDTO {
	private String uid;
	private String upassword;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	@Override
	public String toString() {
		return "UserDTO [uid=" + uid + ", upassword=" + upassword + "]";
	}
}
