package com.koreait.user;
import util.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.boarddb.UserDAO;
import com.koreait.model.UserChangeVO;
import com.koreait.model.UserVO;
@WebServlet("/myPage")
public class MyPageSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String typ = request.getParameter("typ");
		System.out.println("typ :" + typ);
		String jsp = "/WEB-INF/jsp/";
		switch (typ) {
		case "1":
			jsp += "changePw.jsp";
			break;
		}
		
		request.getRequestDispatcher(jsp).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String typ = request.getParameter("typ");
		System.out.println("typ : " + typ);
		UserVO loginUser = Utils.getLoginUser(request);
		try {
			switch(typ) {
				case "1": //비밀번호 수정			
					procTyp1(loginUser, request, response);
					break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void procTyp1(UserVO loginUser, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserChangeVO param = new UserChangeVO();
		String currentPw = request.getParameter("currentPw");
		String changePw = request.getParameter("changePw");
		System.out.println("currentPw : " + currentPw);
		System.out.println("changePw: " + changePw);
		param.setI_user(loginUser.getI_user());
		param.setCurrentPw(currentPw);
		param.setChangePw(changePw);
		int result = UserDAO.changePw(param);
		if(result ==1) {
			Utils.logout(request);
			response.sendRedirect("login");
			return;
		}
		String msg = "";
		switch (result) {
		case 0:
			msg = "기존 비밀번호를 확인해 주세요.";
			break;
		case 2:
			msg = "통신 에러 발생";
			break;
		}
		request.setAttribute("msg", msg);
		doGet(request, response);
	}
}
