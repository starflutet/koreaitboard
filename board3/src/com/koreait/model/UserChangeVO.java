package com.koreait.model;

public class UserChangeVO extends UserVO{
	private String changePw;
	private String currentPw;
	public String getChangePw() {
		return changePw;
	}
	public void setChangePw(String changePw) {
		this.changePw = changePw;
	}
	public String getCurrentPw() {
		return currentPw;
	}
	public void setCurrentPw(String currentPw) {
		this.currentPw = currentPw;
	}
	
}
