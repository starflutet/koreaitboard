package com.koreait.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.boarddb.UserDAO;
import com.koreait.model.UserVO;


@WebServlet("/login")
public class LoginSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private void autoLogin(HttpServletRequest request) {
		HttpSession hs = request.getSession();
		UserVO loginUser = new UserVO();
		loginUser.setI_user(41);
		loginUser.setCid("pika");
		loginUser.setNm("피카츄");
		hs.setAttribute("loginUser", loginUser);
		//자동 로그인
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		//autoLogin(request);
		if(hs.getAttribute("loginUser")!=null) {
			response.sendRedirect("boardList");
			return;
		} 
		String jsp = "/WEB-INF/jsp/login.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		String cpw = request.getParameter("cpw");
		
		System.out.println("cid : " + cid);
		System.out.println("cpw : " + cpw);
		
		UserVO param = new UserVO();
		param.setCid(cid);
		param.setCpw(cpw);
		
		int result = UserDAO.login(param);
		if(result == 1) {
			HttpSession hs = request.getSession();
			param.setCpw(null);
			hs.setAttribute("loginUser", param);
			response.sendRedirect("boardList ");
			return; //리턴을 반드시 해줘야함 이렇게하면 else문을 안써도 됨
		}
		
		String msg = "에러발생";			
		switch(result) {
			case 2:
				msg = "아이디가 없습니다.";
				break;
			case 3:
				msg = "비밀번호를 확인해 주세요.";
				break;
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("writedCid", cid);			
		doGet(request, response);
		
		/*
		int result = UserDAO.login(param);		
		
		if(result == 1) {
			HttpSession hs = request.getSession();
			param.setI_user(param.getI_user());
			param.setNm(param.getNm());
			param.setCpw(null);
			hs.setAttribute("loginUser", param);
			System.out.println("로긴성공!");
			response.sendRedirect("/boardList");
			//세션이기 때문에 샌드 리다이렉트를 해줘도 세션은 웹브라우저가 꺼졌을때 날아가기 때문에 저장되어있다.
		} else if(result == 3) {
			request.setAttribute("data", param);
			request.setAttribute("msg", "잘못된 비밀번호 입니다.");
			doGet(request, response);
		} else if(result == 2) {
			request.setAttribute("data", param);
			request.setAttribute("msg", "아이디가 없습니다.");
			doGet(request, response);
		} else {
			System.out.println("디비같은 그외 에러!");
		}*/
	}
}
