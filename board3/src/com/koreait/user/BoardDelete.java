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
import com.koreait.boarddb.BoardLikeDAO;
import com.koreait.model.BoardLikeVO;
import com.koreait.model.BoardListModel;
import com.koreait.model.BoardVO;
import com.koreait.model.UserVO;



@WebServlet("/boardDel")
public class BoardDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i_board = Integer.parseInt(request.getParameter("i_board"));
		HttpSession hs = request.getSession();
		UserVO loginUser = (UserVO)hs.getAttribute("loginUser");
		
		if(loginUser == null) {
			response.sendRedirect("login");
			return;
		}
		
		BoardVO param = new BoardVO();
		BoardLikeVO bl = new BoardLikeVO();
		param.setI_board(i_board);
		param.setI_user(loginUser.getI_user());
		bl.setI_board(i_board);
		bl.setI_user(loginUser.getI_user());
		//BoardLikeDAO.disableLike(bl);
		int result = BoardDAO.delBoard(param); //0:삭제 실패, 1:삭제 완료
		if(result == 0) { //디테일
			String url = String.format("boardDetail?i_board=%d&error=1", i_board);
			response.sendRedirect(url);
			request.setAttribute("msg", "삭제 실패");
			return;
		} 
		
		response.sendRedirect("/boardList");
		/*
		HttpSession hs = request.getSession();		
		if(hs.getAttribute("loginUser") == null) {
			response.sendRedirect("/login");
			return;
		}
		int i_board = Integer.parseInt(request.getParameter("i_board"));		
		BoardDAO.boardDelete(i_board);
		response.sendRedirect("/boardList");*/
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
