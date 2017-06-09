package com.argSecurity.model.dto;

public class UserPwdDTO {
	
	private String old;
	
	private String newPwd;

	public String getOld() {
		return old;
	}

	public void setOld(String old) {
		this.old = old;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public UserPwdDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserPwdDTO(String old, String newPwd) {
		super();
		this.old = old;
		this.newPwd = newPwd;
	}
	
	

}
