package com.koreait.model;

public class BoardListModel extends BoardVO{
	private String userNm;
	private int cnt;
	private int likeUser;
	private int startIdx;
	private int endIdx;
	public int getStartIdx() {
		return startIdx;
	}

	public void setStartIdx(int startIdx) {
		this.startIdx = startIdx;
	}

	public int getEndIdx() {
		return endIdx;
	}

	public void setEndIdx(int endIdx) {
		this.endIdx = endIdx;
	}

	public int getLikeUser() {
		return likeUser;
	}

	public void setLikeUser(int likeUser) {
		this.likeUser = likeUser;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	
	
}
