package com.koreait.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.boarddb.BoardDAO;
import com.koreait.boarddb.CmtDAO;
import com.koreait.model.BoardListModel;
import com.koreait.model.BoardVO;
import com.koreait.model.CmtVO;
import com.koreait.model.UserVO;



@WebServlet("/boardDetail")
public class BoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		UserVO loginUser = (UserVO)hs.getAttribute("loginUser");
		if(loginUser == null) {
			response.sendRedirect("login");
			return;
		}
		String mod = request.getParameter("mod");
		
		String err = request.getParameter("err");
		if(err != null) {
			String msg = "";
			switch(err) {
			case "1":
				msg = "삭제 실패";
				break;
			case "2":
				msg = "수정 실패";
				break;
			}
			request.setAttribute("msg",  msg);
		}
		
		int i_board = Integer.parseInt(request.getParameter("i_board"));
		
		ServletContext application = getServletContext();
		Integer lastViewUser = (Integer)application.getAttribute("board" + i_board);		
		if(lastViewUser == null || lastViewUser != loginUser.getI_user()) {			
			BoardDAO.updCntAdd(i_board);
			application.setAttribute("board" + i_board, loginUser.getI_user());
		}
		
		BoardListModel param = new BoardListModel();
		param.setI_board(i_board);
		param.setI_user(loginUser.getI_user());
		
		request.setAttribute("data", BoardDAO.selectBoardDetail(param));
		
		String jsp = "/WEB-INF/jsp/boardDetail.jsp";
		if("mod".equals(mod)) {
			jsp = "/WEB-INF/jsp/boardRegmod.jsp";
		}
		
		CmtVO cm = new CmtVO();
		cm.setI_board(i_board);
		request.setAttribute("cmtdata", CmtDAO.selectListCmt(cm));
		request.getRequestDispatcher(jsp).forward(request, response);
	}
	//내가 쓴글이면 수정 삭제 버튼 나오고 내가 쓴 글이 아니면 수정 삭제버튼 안나오게하기?
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
