package com.koreait.boarddb;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.koreait.model.BoardLikeVO;

public class BoardLikeDAO {
	public static int enableLike(BoardLikeVO param) {//insert
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		String sql = " insert into t_board3_like(i_board,i_user) values(?,?)";
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_board());
			ps.setInt(2, param.getI_user());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps);
		}
		return result;
	}
	
	public static int disableLike(BoardLikeVO param) {//delete
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		String sql = " DELETE FROM t_board3_like WHERE i_user = ? AND i_board = ?";
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_user());
			ps.setInt(2, param.getI_board());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con, ps);
		}
		return result;
	}
}
