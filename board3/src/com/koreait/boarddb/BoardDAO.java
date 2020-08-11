package com.koreait.boarddb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.model.BoardListModel;
import com.koreait.model.BoardVO;


public class BoardDAO {
	public static int selectTotalPageCnt(int recordCnt) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT CEIL(COUNT(i_board)/?) FROM t_board3";
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, recordCnt);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbCon.close(con, ps, rs);
		}
		return result;
	}
	public static void updCntAdd(int i_board) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = " UPDATE t_board3 " + 
				" set cnt = cnt +1 " + 
				" where i_board = ?";
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, i_board);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static int regBoard(BoardVO param) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i_board = 0;
		
		String sql = " INSERT INTO t_board3 "
				+ " (i_board, title, ctnt, i_user) "
				+ " VALUES "
				+ " (seq_board.nextval, ?, ?, ?) ";
		
		String cols[] = {"i_board"}; //인서트 되고 난 후의 값을 얻고싶을때 셀렉트 두번하기싫어서 쓰는 방법
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql, cols);
			ps.setNString(1, param.getTitle());
			ps.setNString(2, param.getCtnt());
			ps.setInt(3,  param.getI_user());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			while(rs.next()) {				
				i_board = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps);
		}
		return i_board;
	}
	/*public static void regBoard(BoardVO vo) {
		Connection con =null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO t_board3(i_board,title,ctnt,i_user) values(seq_board.nextval, ?, ?,?)";
		try {
			 con = DbCon.getCon();
			 ps = con.prepareStatement(sql);
			 ps.setString(1, vo.getTitle());
			 ps.setString(2, vo.getCtnt());
			 ps.setInt(3, vo.getI_user());
			 ps.executeUpdate(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con,ps);
		}
	}*/
	public static void updateBoard(BoardVO param) {
		Connection con =null;
		PreparedStatement ps = null;
		String sql = "UPDATE t_board3 SET title = ?, ctnt = ?, rn_dt = sysdate WHERE i_board = ? AND i_user = ?";
		try {
			 con = DbCon.getCon();
			 ps = con.prepareStatement(sql);
			 ps.setString(1, param.getTitle());
			 ps.setString(2, param.getCtnt());
			 ps.setInt(3, param.getI_board());
			 ps.setInt(4, param.getI_user());
			 ps.executeUpdate(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con,ps);
		}
	}
	
	public static List<BoardListModel> selectBoardList(BoardListModel param){
		List<BoardListModel> list = new ArrayList<BoardListModel>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT * " + 
				"FROM ( " + 
				" SELECT ROWNUM AS RNUM, A.* " + 
				" FROM ( " + 
				" SELECT A.i_board,A.title,TO_CHAR(A.r_dt ,'yyyy.mm.dd') as r_dt, A.cnt,B.i_user,B.nm as userNm, " + 
				" NVL(C.i_user,0) as likeUser FROM t_board3 A INNER JOIN t_user3 B ON A.i_user = B.i_user " + 
				" LEFT JOIN t_board3_like C ON A.i_board=C.i_board AND C.i_user = ? " + 
				" ORDER BY A.i_board DESC " + 
				" ) A WHERE ROWNUM <= ? " + 
				") " + 
				"WHERE RNUM > ? ";
		try {
			con=DbCon.getCon();
			ps=con.prepareStatement(sql);
			ps.setInt(1, param.getI_user());
			ps.setInt(2, param.getEndIdx());
			ps.setInt(3, param.getStartIdx());
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardListModel lm = new BoardListModel();
				lm.setI_board(rs.getInt("i_board"));
				lm.setTitle(rs.getNString("title"));
				lm.setR_dt(rs.getNString("r_dt"));
				lm.setI_user(rs.getInt("i_user"));
				lm.setUserNm(rs.getNString("userNm"));
				lm.setCnt(rs.getInt("cnt"));
				lm.setLikeUser(rs.getInt("likeUser"));
				list.add(lm);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps, rs);
		}
		return list;
	}
	
	
	
	
	
/*
	public static List<BoardListModel> selectBoardList(){
		List<BoardListModel> list = new ArrayList<BoardListModel>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT "+ 
				" A.i_board, A.title, TO_CHAR(A.r_dt ,'yyyy.mm.dd') as r_dt, B.i_user,B.nm as userNm " + 
				" FROM t_board3 A " + 
				" INNER JOIN t_user3 B " + 
				" ON A.i_user = B.i_user " + 
				" ORDER BY i_board DESC ";
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardListModel bm = new BoardListModel();
				bm.setI_board(rs.getInt("i_board"));
				bm.setTitle(rs.getNString("title"));
				bm.setR_dt(rs.getNString("r_dt"));
				bm.setI_user(rs.getInt("i_user"));
				bm.setUserNm(rs.getNString("userNm"));
				list.add(bm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con,ps,rs);
		}
		return list;
	}*/

	
	public static BoardListModel selectBoardDetail(BoardListModel param) {
		BoardListModel result = null;
		Connection con = null;
		PreparedStatement ps = null;//얘는 Prepared에서 d가 들어감  메소드에선 d가 없네 헐
		ResultSet rs = null;
		
		String sql = "  SELECT A.i_board, A.title, A.r_dt, A.ctnt,A.cnt, B.i_user , B.nm as userNm,NVL(C.i_user,0) as likeUser" + 
				"  FROM t_board3 A INNER JOIN t_user3 B ON A.i_user = B.i_user" + 
				"  LEFT JOIN t_board3_like C ON A.i_board = C.i_board AND C.i_user = ? " + 
				"  where A.i_board = ?";
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_user());
			ps.setInt(2, param.getI_board());
			rs = ps.executeQuery();
			while (rs.next()) {
				result = new BoardListModel();
				
				String title = rs.getNString("title");
				String ctnt = rs.getNString("ctnt");
				String r_dt = rs.getNString("r_dt");
				int i_user = rs.getInt("i_user");
				String userNm = rs.getNString("userNm");
				int cnt = rs.getInt("cnt");
				int likeUser = rs.getInt("likeUser");
				
				result.setCnt(cnt);
				result.setI_board(param.getI_board());
				result.setTitle(title);
				result.setCtnt(ctnt);
				result.setR_dt(r_dt);
				result.setI_user(i_user);
				result.setUserNm(userNm);
				result.setLikeUser(likeUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con,ps,rs);
		}
		return result;
	}
	public static int delBoard(BoardVO param) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		//밑에 처럼 sql문을 2번 나눠서 실행하는게 가능하다!
		//대신 ps.close()로 꺼줘야한다!
		//con.setAutoCommit(false)를 통해 자동으로 커밋되는걸 끌수있다!
		//다하고나면 불안하니까 setAutoCommit(true)를 하자! 
		String sql = " DELETE FROM t_board3_like WHERE i_board = ? "; 
		String sql2 = " DELETE FROM t_board3 WHERE i_board = ? AND i_user = ? ";
		try {
			con = DbCon.getCon();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_board());
			result = ps.executeUpdate(); 
			ps.close();
			System.out.println("좋아요 테이블 삭제 레코드 수 : " +result);
			ps = con.prepareStatement(sql2);
			ps.setInt(1, param.getI_board());
			ps.setInt(2, param.getI_user());
			result = ps.executeUpdate();
			if(result == 0) {
				con.rollback();
			} else {
				con.commit();
			}
			con.setAutoCommit(true);
		} catch (Exception e) {
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			DbCon.close(con, ps);
		}
		return result;
	}
	
}
