package com.koreait.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.boarddb.CmtDAO;
import com.koreait.model.CmtVO;
import com.koreait.model.UserVO;

@WebServlet("/boardCmt")
public class CmtRegSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stri_board = request.getParameter("i_board");
		int i_board = Integer.parseInt(stri_board);
		HttpSession hs = request.getSession();
		UserVO loginUser = (UserVO) hs.getAttribute("loginUser");
		String stri_cmt = request.getParameter("i_cmt");
		int i_cmt = Integer.parseInt(stri_cmt);
		System.out.println("i_cmt : "+ i_cmt);
		CmtVO cm = new CmtVO();
		cm.setI_user(loginUser.getI_user());
		cm.setI_cmt(i_cmt);
		cm.setI_board(i_board);
		CmtDAO.deleteCmt(cm);
		response.sendRedirect("boardDetail?i_board="+i_board);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		UserVO loginUser = (UserVO) hs.getAttribute("loginUser");
		String mod = request.getParameter("mod");
		if(hs.getAttribute("loginUser")==null) {
			response.sendRedirect("login");
			return;
		} 
		String stri_board = request.getParameter("i_board");
		String cmt = request.getParameter("cmt");
		int i_board = Integer.parseInt(stri_board);
		System.out.println("mod : " + mod);
		System.out.println("i_board : " + i_board);
		System.out.println("cmt : "+ cmt);
		System.out.println("i_user : "+ loginUser.getI_user());
		CmtVO cm = new CmtVO();
		cm.setI_board(i_board);
		cm.setCmt(cmt);
		cm.setI_user(loginUser.getI_user());
		String url ="boardDetail?i_board=";
		if(("mod").equals(mod)) {
			String stri_cmt = request.getParameter("i_cmt");
			System.out.println("stri_cmt : "+ stri_cmt);
			int i_cmt = Integer.parseInt(stri_cmt);
			cm.setI_cmt(i_cmt);
			CmtDAO.cmtMod(cm);
			response.sendRedirect(url+i_board);
			return;
		}
		CmtDAO.insertCmt(cm);
		response.sendRedirect(url + i_board);
	}
}

