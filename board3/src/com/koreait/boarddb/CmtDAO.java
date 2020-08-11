package com.koreait.boarddb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.koreait.model.CmtVO;

public class CmtDAO {
	public static int cmtMod(CmtVO cm) {
		Connection con=null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = " UPDATE t_board3_cnt SET cmt = ? where i_cmt = ? "; 
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, cm.getCmt());
			ps.setInt(2, cm.getI_cmt());
			result = ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbCon.close(con, ps);
		}
		return result;
	}
	public static List<CmtVO> selectListCmt(CmtVO cm) {
		List<CmtVO> list = new ArrayList<CmtVO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = " SELECT A.i_cmt,A.i_board,A.cmt,TO_CHAR(A.r_dt,'yyyy.mm.dd') as r_dt,A.i_user,B.nm as userName "
				+ " from t_board3_cnt A "
				+ " INNER JOIN t_user3 B "
				+ " ON A.i_user = B.i_user WHERE i_board = ? "
				+ " ORDER BY i_cmt asc ";
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, cm.getI_board());
			rs = ps.executeQuery();
			while (rs.next()) {
				CmtVO cmt = new CmtVO();
				cmt.setI_cmt(rs.getInt("i_cmt"));
				cmt.setI_board(rs.getInt("i_board"));
				cmt.setCmt(rs.getNString("cmt"));
				cmt.setR_dt(rs.getNString("r_dt"));
				cmt.setI_user(rs.getInt("i_user"));
				cmt.setCmtNm(rs.getNString("userName"));
				list.add(cmt);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbCon.close(con, ps, rs);
		}
		return list;
	}
	public static int insertCmt(CmtVO cm) {
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		String sql = "  INSERT INTO t_board3_cnt(i_cmt,i_board,i_user,cmt) "
				+ "values(seq_cmt.nextval,?,?,?) ";
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, cm.getI_board());
			ps.setInt(2, cm.getI_user());
			ps.setNString(3, cm.getCmt());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbCon.close(con, ps);
		}
		return result;
	}
	public static void deleteCmt(CmtVO cm) {
		PreparedStatement ps = null;
		Connection con = null;
		String sql = " DELETE FROM t_board3_cnt "
				+ " WHERE i_cmt = ? and i_user= ? and i_board = ?";
		try {
			con =DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, cm.getI_cmt());
			ps.setInt(2, cm.getI_user());
			ps.setInt(3, cm.getI_board());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbCon.close(con, ps);
		}
	}
}
