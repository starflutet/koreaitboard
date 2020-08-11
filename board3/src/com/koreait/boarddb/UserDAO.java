package com.koreait.boarddb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.koreait.model.UserChangeVO;
import com.koreait.model.UserVO;


public class UserDAO {
	public static int changePw(UserChangeVO param) {
		int result = 2;
		String sql = " UPDATE t_user3 "
				+ " SET cpw = ? "
				+ " WHERE i_user = ? "
				+ " AND cpw = ? " ;
		Connection con = null;
		PreparedStatement ps= null;
		try {
			con=DbCon.getCon();
			ps =con.prepareStatement(sql);
			ps.setNString(1, param.getChangePw());
			ps.setInt(2, param.getI_user());
			ps.setNString(3, param.getCurrentPw());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbCon.close(con, ps);
		}
		return result;
	}
	//성공이면 1 실패면 0
	public static int join(UserVO param) {
		int result = 0;
		String sql="INSERT INTO t_user3(i_user,cid,cpw,nm)VALUES(seq_user.nextval,?,?,?)";
		Connection con = null;
		PreparedStatement ps= null;
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, param.getCid());
			ps.setString(2, param.getCpw());
			ps.setString(3, param.getNm());
			result = ps.executeUpdate(); 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DbCon.close(con,ps);
		}
		return result;
	}
	
	//1. 로그인 성공, 2. 아이디없음, 3. 비밀번호 틀림, 0. 에러발생
	public static int login(UserVO param) {
		String sql="SELECT i_user,nm,cpw FROM t_user3 WHERE cid = ?";
		//UserVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, param.getCid());
			rs=ps.executeQuery();
			if(rs.next()) {
				if(rs.getNString("cpw").equals(param.getCpw())) {
					param.setI_user(rs.getInt("i_user"));
					param.setNm(rs.getNString("nm"));
					return 1; //로긴 성공
				}
				else {
					return 3; //비밀번호 에러
				}
			}
			return 2; //아이디없음
		} catch (Exception e) {
				e.printStackTrace();
		} finally {
			DbCon.close(con, ps, rs);
		}
		return 0; //에러발생
	}
	public static int chkId(String cid) {
		int result = 0;
		String sql = "select count(cid) from t_user3 where cid= ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, cid);
			rs = ps.executeQuery();
			while(rs.next()) {
				result = rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbCon.close(con, ps, rs);
		}
		return result;
		
	}
}
