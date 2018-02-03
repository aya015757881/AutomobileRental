package com.oaec.tool;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper
{
	static private String driver = "oracle.jdbc.driver.OracleDriver";
	static private String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	static private String user = "aya";
	static private String password = "123456";
	static private Connection conn;
	static private PreparedStatement pstm;
	
	/**
	 * 与数据库形成连接。
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public static void getConnected() throws SQLException, ClassNotFoundException
	{
		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * 接收一个SQL语句，得到一个有效的Preparedstatement对象。
	 * @param sql
	 * @throws SQLException
	 */
	public static void getSQL(String sql) throws SQLException
	{
		pstm = conn.prepareStatement(sql);
	}
	
	/**
	 * pstm的getter方法。
	 * @return
	 */
	public static PreparedStatement getPstm() {
		return pstm;
	}
	
	/**
	 * pstm的setter方法。
	 * @param pstm
	 */
	public static void setPstm(PreparedStatement pstm) {
		DBHelper.pstm = pstm;
	}
}
