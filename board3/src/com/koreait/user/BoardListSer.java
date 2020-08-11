package com.koreait.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.boarddb.BoardDAO;
import com.koreait.model.BoardListModel;
import com.koreait.model.UserVO;



@WebServlet("/boardList")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int recordCnt = 5;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		if(hs.getAttribute("loginUser")==null) {
			response.sendRedirect("login");
			return;
		} 
		String strPage = request.getParameter("page");
		int page = 1;
		if(strPage !=null) {
			page = Integer.parseInt(strPage);
		}
		System.out.println("page : "+page);
		request.setAttribute("totalPageCnt", BoardDAO.selectTotalPageCnt(recordCnt));
		int endIdx = page*recordCnt;
		int startIdx = endIdx -recordCnt;
		
		UserVO loginUser = (UserVO)hs.getAttribute("loginUser"); 
		BoardListModel param = new BoardListModel(); 
		param.setI_user(loginUser.getI_user()); 
		param.setEndIdx(endIdx);
		param.setStartIdx(startIdx);
		request.setAttribute("data", BoardDAO.selectBoardList(param));
		String jsp = "/WEB-INF/jsp/boardList.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		hs.invalidate();
		response.sendRedirect("login");
	}
}
