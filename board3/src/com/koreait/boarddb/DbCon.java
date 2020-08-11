package com.koreait.boarddb;

import java.sql.*;

public class DbCon {
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String id = "korea01";
	private static final String pw = "1234";

	public static Connection getCon() throws Exception {//OracleDB, mySqlDB, mariaDB
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, id, pw);
		System.out.println("연결 성공!");
		return con;
	}

	public static void close(Connection con, PreparedStatement ps) { 
		// insert delete update
		// ps.executeUdate(); 무적권 실행용 인트 숫자담겨
		// execute 얘의 역할은 실행
		close(con, ps, null);
	}

	public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
		// select xxxx
		// rs 뭐다 리스트 쿼리 결과내용을 담는것
		// rs = ps.executeQuery();
		
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}
}
