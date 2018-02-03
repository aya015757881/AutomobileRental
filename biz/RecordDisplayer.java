package com.oaec.biz;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.dao.DBOperator;
import com.oaec.tool.Keyboard;

public class RecordDisplayer
{
	static String userItems = "select r.id ���ޱ��,r.car_id �������,c.model ��������,c.rent ÿ�����,"
			+ "r.payment �����,c.t_comments ��ע,bra.name Ʒ��,cat.name ����,"
			+ "r.start_date �⳵ʱ��,r.return_date ����ʱ��  ";
	
	static String adminItems = "select r.id ���ޱ��,r.car_id �������,c.model ��������,u.id �û����,u.username �û���,"
			+ "c.rent ÿ�����,r.payment �����,c.t_comments ��ע,bra.name Ʒ��,cat.name ����,"
			+ "r.start_date �⳵ʱ��,r.return_date ����ʱ��  ";
	
	static String rest = "from t_record r,t_car c,t_brand bra,t_category cat,t_user u where "
			+ "c.brand_id = bra.id and c.category_id = cat.id and u.id = r.user_id and r.car_id = c.id ";
	
	static String sqlU = userItems + rest + "and u.username = ? ";
	
	static String sqlA = adminItems + rest;
	
	static String SQL;
	
	static ResultSet rs;
	
	/**
	 * ����Ա��ѯȫ�����޼�¼��
	 * @throws SQLException
	 */
	public static void showAll() throws SQLException
	{
		SQL = sqlA + "order by r.id";
		rs = DBOperator.getResult(SQL);
		if(rs.next())
		{
			System.out.println("ȫ�����޼�¼���£�");
			showRecords(rs,"admin");
		}
		else
			System.out.println("��ǰû�����޼�¼\n");
	}
	
	/**
	 * ����Ա����ָ����������ţ���ѯ�ñ�Ŷ�Ӧ�����������޼�¼��
	 * @param carId �����ı��
	 * @throws SQLException
	 */
	public static void showByCarId(int carId) throws SQLException
	{
		SQL = sqlA + "and r.car_id = ?";
		rs = DBOperator.getResult(SQL,carId);
		String carName = Getter.getCarName(carId);
		if("".equals(carName))
			System.out.println("���޴˳�\n");
		else if(rs.next())
		{
			System.out.println(carName+"���������޼�¼���£�");
			showRecords(rs,"admin");
		}
		else
			System.out.println(carName+"��ǰû�����޼�¼\n");
	}
	
	/**
	 * ����Ա����ָ�����û�������ѯ���û������޼�¼��
	 * @throws SQLException
	 */
	public static void showByUser() throws SQLException
	{
		System.out.println("������ָ���û����û�����");
		String username = Keyboard.next();
		SQL = sqlA + "and u.username = ?";
		if(Getter.getUserId(username)>0)
		{
			rs = DBOperator.getResult(SQL,username);
			if(rs.next())
			{
				System.out.println(username+"�����޼�¼���£�");
				showRecords(rs,"admin");
			}
			else
				System.out.println(username+"��ǰû�����޼�¼\n");
		}
		else
			System.out.println("���޴��û�\n");
	}
	
	/**
	 * �û���ѯ�Լ������޼�¼��
	 * @param username �û���
	 * @throws SQLException
	 */
	public static void showUserRecord(String username) throws SQLException
	{
		rs = DBOperator.getResult(sqlU,username);
		if(rs.next())
		{
			System.out.println(username+"���⳵��¼���£�");
			showRecords(rs,"user");
		}
		else
			System.out.println(username+"��ǰû���⳵��¼\n");
	}
	
	/**
	 * ��ʾ�û��ո�����������������޼�¼��
	 * @param username �⳵���û����û���
	 * @param carId ���������ı��
	 * @throws SQLException
	 */
	public static void showRentRecord(String username,int carId) throws SQLException
	{
		SQL = sqlU + "and r.car_id = ? and r.payment = 0";
		rs = DBOperator.getResult(SQL,username,carId);
		if(rs.next())
			showRecords(rs,"user");
	}
	
	/**
	 * ��ʾ�û��ոջ����������������޼�¼��
	 * @param username �������û����û���
	 * @param recordId ���������ı��
	 * @throws SQLException
	 */
	public static void showReturnRecord(String username,int recordId) throws SQLException
	{
		SQL = sqlU + "and r.id = ?";
		rs = DBOperator.getResult(SQL,username,recordId);
		if(rs.next())
			showRecords(rs,"user");
	}
	
	/**
	 * �����û����͵Ĳ�ͬ����ʾ��ͬ�������޼�¼�������
	 * @param rs ���޼�¼�����
	 * @param userType �û����ͣ���ͨ�û������Ա��
	 * @throws SQLException
	 */
	private static void showRecords(ResultSet rs,String userType) throws SQLException
	{
		showBound(userType);
		showRecordLabel(userType);
		showRecordContent(rs,userType);
		while(rs.next())
			showRecordContent(rs,userType);
		System.out.println();
	}
	
	/**
	 * �����û����͵Ĳ�ͬ����ʾ��ͬ���ı߿�
	 * @param userType �û����ͣ���ͨ�û������Ա��
	 */
	private static void showBound(String userType)
	{
		for(int i=0;i<("admin".equals(userType)?110:90);i++)
			System.out.print("=");
		System.out.println();
	}
	
	/**
	 * �����û����͵Ĳ�ͬ����ʾ��ͬ���͵����޼�¼��ͷ��
	 * @param userType �û����ͣ���ͨ�û������Ա��
	 */
	private static void showRecordLabel(String userType)
	{
		System.out.print("���ޱ��\t");
		System.out.print("�������\t");
		System.out.print("��������\t");
		if("admin".equals(userType))
		{
			System.out.print("�û����\t");
			System.out.print("�û���\t");
		}
		System.out.print("ÿ�����\t");
		System.out.print("�����\t");
		System.out.print("��ע\t");
		System.out.print("Ʒ��\t");
		System.out.print("����\t");
		System.out.print("�⳵ʱ��\t\t");
		System.out.println("����ʱ��");
	}
	
	/**
	 * �����û����͵Ĳ�ͬ����ӡ����ͬ���͵����޼�¼���������Ϣ��
	 * @param rs ���޼�¼�����
	 * @param userType �û�����
	 * @throws SQLException 
	 */
	private static void showRecordContent(ResultSet rs,String userType) throws SQLException
	{
		System.out.print(rs.getLong("���ޱ��")+"\t");
		System.out.print(rs.getLong("�������")+"\t");
		System.out.print(rs.getString("��������")+"\t");
		if("admin".equals(userType))
		{
			System.out.print(rs.getInt("�û����")+"\t");
			System.out.print(rs.getString("�û���")+"\t");
		}
		System.out.print(rs.getDouble("ÿ�����")+"\t");
		System.out.print(rs.getDouble("�����")==0?"δ��\t":rs.getDouble("�����")+"\t");
		System.out.print(rs.getString("��ע")+"\t");
		System.out.print(rs.getString("Ʒ��")+"\t");
		System.out.print(rs.getString("����")+"\t");
		System.out.print(rs.getDate("�⳵ʱ��")+"\t");
		System.out.println(rs.getDate("����ʱ��")==null?"δ��":rs.getDate("����ʱ��"));
	}
}
