package com.koreait.model;

public class CmtVO{
	private int i_cmt;
	private int i_board;
	private int i_user;
	private String cmt;
	private String r_dt;
	private String cmtNm;
	public String getCmtNm() {
		return cmtNm;
	}
	public void setCmtNm(String cmtNm) {
		this.cmtNm = cmtNm;
	}
	public int getI_cmt() {
		return i_cmt;
	}
	public void setI_cmt(int i_cmt) {
		this.i_cmt = i_cmt;
	}
	public int getI_board() {
		return i_board;
	}
	public void setI_board(int i_board) {
		this.i_board = i_board;
	}
	public int getI_user() {
		return i_user;
	}
	public void setI_user(int i_user) {
		this.i_user = i_user;
	}
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public String getR_dt() {
		return r_dt;
	}
	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
	}
}
