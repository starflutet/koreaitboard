package com.koreait.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.boarddb.BoardDAO;
import com.koreait.model.BoardVO;
import com.koreait.model.UserVO;

@WebServlet("/boardReg")
public class BoardRegSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	//등록화면 화면 띄우기?
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsp = "/WEB-INF/jsp/boardRegmod.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);
	}
	
	//처리(reg,mod 같이 줄거임) ,처리부분
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strI_board = request.getParameter("i_board");
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		
		System.out.println("i_board : " + strI_board);
		System.out.println("title : " + title);
		System.out.println("ctnt : " + ctnt);
		
		HttpSession hs = request.getSession();
		UserVO loginUser = (UserVO)hs.getAttribute("loginUser");
		BoardVO param = new BoardVO();
		param.setTitle(title);
		param.setCtnt(ctnt);
		param.setI_user(loginUser.getI_user());
		
		if("".equals(strI_board)) { //등록
			int i_board = BoardDAO.regBoard(param);
			//보드디테일 화면으로 이동
			
			//리스트화면으로 이동
			response.sendRedirect("boardDetail?i_board="+i_board);
		
		} else { //수정
			int i_board = Integer.parseInt(strI_board);
			param.setI_board(i_board);
			BoardDAO.updateBoard(param);
			response.sendRedirect("boardDetail?i_board="+i_board);
		}
	}
}
