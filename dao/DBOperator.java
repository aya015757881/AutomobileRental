package com.oaec.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import com.oaec.tool.DBHelper;

public class DBOperator
{
	/**
	 * ���ݾ����sql��ѯ���Ͳ�����ѯ���ݿ���Ϣ��������һ������Ľ������
	 * @param sql �����sql���
	 * @param obj sql�е�ͨ�������Ӧ�Ĳ���
	 * @return ���ز�ѯ���Ľ����
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
	 * ���ݾ����sql�������Ͳ����������ݿ���Ϣ��
	 * @param sql �����sql�������
	 * @param obj sql�е�ͨ�������Ӧ�Ĳ���
	 * @return �������ݿ��������������һ�е�����������û�в����򷵻�0
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
	 * �����ݿ������ûع���A��
	 * @throws SQLException
	 */
	public static void savePoint() throws SQLException
	{
		DBHelper.getSQL("savepoint A");
		DBHelper.getPstm().execute();
	}
	
	/**
	 * �����ݿ⻹ԭ���ع���A��
	 * @throws SQLException
	 */
	public static void rollBack() throws SQLException
	{
		DBHelper.getSQL("rollback to A");
		DBHelper.getPstm().execute();
	}
	
	/**
	 * �ύ�����ݿ�Ĳ�����
	 * @throws SQLException
	 */
	public static void commit() throws SQLException
	{
		DBHelper.getSQL("commit");
		DBHelper.getPstm().execute();
	}
}
