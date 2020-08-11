package com.koreait.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.boarddb.BoardLikeDAO;
import com.koreait.model.BoardLikeVO;
import com.koreait.model.UserVO;
@WebServlet("/boardLike")
public class BoardLikeSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i_board = Integer.parseInt(request.getParameter("i_board"));
		int isLike = Integer.parseInt(request.getParameter("isLike")); //기존에 좋아요했었는지 안 했었는지 구분용!!!
		
		int result = 0;
		HttpSession hs = request.getSession();
		UserVO loginUser = (UserVO)hs.getAttribute("loginUser");
		BoardLikeVO param = new BoardLikeVO();
		param.setI_board(i_board);
		param.setI_user(loginUser.getI_user());
		if(isLike == 0) { //좋아요 enable 해줘야함
			result = BoardLikeDAO.enableLike(param);
		} else { //disable 해줘야함 (좋아요 취소)
			result = BoardLikeDAO.disableLike(param);
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String res = String.format("{\"result\": %d}", result);//변수명 지정할땐 \" 이고 스트링 포멧은 스트링 변수에 값지정해서 넣고싶을때
		out.print(res);
		out.flush();
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
