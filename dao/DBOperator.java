package com.oaec.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import com.oaec.tool.DBHelper;

public class DBOperator
{
	/**
	 * 根据具体的sql查询语句和参数查询数据库信息，并返回一个具体的结果集。
	 * @param sql 具体的sql语句
	 * @param obj sql中的通配符所对应的参数
	 * @return 返回查询到的结果集
	 * @throws SQLException
	 */
	public static ResultSet getResult(String sql,Object... obj) throws SQLException
	{
		DBHelper.getSQL(sql);
		for(int i=0;i<obj.length;i++)
			DBHelper.getPstm().setObject(i+1,obj[i]);
		return DBHelper.getPstm().executeQuery();
	}
	
	/**
	 * 根据具体的sql更新语句和参数更新数据库信息。
	 * @param sql 具体的sql更新语句
	 * @param obj sql中的通配符所对应的参数
	 * @return 返回数据库表中所操作的那一行的行序数，若没有操作则返回0
	 * @throws SQLException
	 */
	public static int update(String sql,Object... obj) throws SQLException
	{
		DBHelper.getSQL(sql);
		for(int i=0;i<obj.length;i++)
			DBHelper.getPstm().setObject(i+1,obj[i]);
		return DBHelper.getPstm().executeUpdate();
	}
	
	/**
	 * 在数据库中设置回滚点A。
	 * @throws SQLException
	 */
	public static void savePoint() throws SQLException
	{
		DBHelper.getSQL("savepoint A");
		DBHelper.getPstm().execute();
	}
	
	/**
	 * 将数据库还原到回滚点A。
	 * @throws SQLException
	 */
	public static void rollBack() throws SQLException
	{
		DBHelper.getSQL("rollback to A");
		DBHelper.getPstm().execute();
	}
	
	/**
	 * 提交对数据库的操作。
	 * @throws SQLException
	 */
	public static void commit() throws SQLException
	{
		DBHelper.getSQL("commit");
		DBHelper.getPstm().execute();
	}
}
