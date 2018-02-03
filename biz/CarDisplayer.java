package com.oaec.biz;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.dao.DBOperator;

public class CarDisplayer
{
	static String userItems = "select c.id ���,c.model ����,c.t_comments ��ע,bra.name Ʒ��,"
			+ "bra.id Ʒ�Ʊ��,cat.name ����,cat.id ���ͱ��,c.rent ���,c.status �Ƿ���� ";

	static String adminItems = userItems + ",c.useable �Ƿ��ϼ� ";
	
	static String rest = "from t_car c,t_brand bra,t_category cat where "
			+ "c.brand_id = bra.id and c.category_id = cat.id ";
	
	static String sqlU = userItems + rest + "and c.useable = 0 ";
	
	static String sqlA = adminItems + rest;
	
	static String SQL;
	
	static ResultSet rs;
	
	/**
	 * ��ѯ��ÿһ����������Ϣ��
	 * ����Ա��ѯ����Ϣ���û���ѯ����Ϣ���һ����Ŀ�������¼�״̬���������Ŀ����ͬ��
	 * @param userType �û����ͣ���ͨ�û������Ա��
	 * @throws SQLException
	 */
	public static void showAll(String userType) throws SQLException
	{
		if("user".equals(userType))
			SQL = sqlU + "order by c.id asc";
		else
			SQL = sqlA + "order by c.id asc";
		rs = DBOperator.getResult(SQL);
		if(rs.next())
		{
			System.out.println("ȫ��������Ϣ���£�");
			showCars(rs,userType);
		}
		else
			System.out.println("��ǰû��������Ϣ\n");
	}
	
	/**
	 * ��ѯָ����ŵ���������Ϣ��
	 * @param carId �����ı��
	 * @throws SQLException
	 */
	public static void showByCarId(int carId) throws SQLException
	{
		SQL = sqlA + "and c.id = ?";
		rs = DBOperator.getResult(SQL,carId);
		if(rs.next())
		{
			System.out.println(Getter.getCarName(carId)+"����Ϣ���£�");
			showCars(rs,"admin");
		}
		else
			System.out.println("���޴˳�\n");
	}
	
	/**
	 * ��ѯָ�����ƺŵ���������Ϣ��
	 * @param carNumber �����ĳ��ƺ�
	 * @throws SQLException
	 */
	public static void showByCarNumber(String carNumber) throws SQLException
	{
		SQL = sqlA + "and c.car_number = ?";
		rs = DBOperator.getResult(SQL,carNumber);
		if(rs.next())
			showCars(rs,"admin");
	}
	
	/**
	 * �������ĸߵͽ������������ȫ���ϼ���������Ϣ
	 * @param order ָ����������ַ���
	 * @throws SQLException
	 */
	public static void showByRent(String order) throws SQLException
	{
		SQL = sqlU + "order by c.rent " + order;
		rs = DBOperator.getResult(SQL);
		if(rs.next())
			showCars(rs,"user");
		else
			System.out.println("��ǰû��������Ϣ\n");
	}
	
	/**
	 * ����ָ��������ID��Ʒ��ID����ѯ�������ͻ�Ʒ�Ƶ���������Ϣ��
	 * @param id Ʒ��ID������ID
	 * @param type ָ����Ʒ�ƻ��ǰ����Ͳ�ѯ�ı���ַ���
	 * @throws SQLException
	 */
	public static void showById(int id,String type) throws SQLException
	{
		String append = "brand".equals(type)?"and c.brand_id = ?":"and c.category_id = ?";
		SQL = sqlU + append;
		rs = DBOperator.getResult(SQL,id);
		if(rs.next())
			showCars(rs,"user");
		else
			System.out.println("��ǰû��������Ϣ\n");
	}
	
	/**
	 * �����û����͵Ĳ�ͬ����ʾ��ͬ����������Ϣ�Ľ������
	 * @param rs ������Ϣ�����
	 * @param userType �û����ͣ���ͨ�û������Ա��
	 * @throws SQLException
	 */
	private static void showCars(ResultSet rs,String userType) throws SQLException
	{
		showBound(userType);
		showCarLabel(userType);
		showCarInfo(rs,userType);
		while(rs.next())
			showCarInfo(rs,userType);
		System.out.println();
	}
	
	/**
	 * �����û����͵Ĳ�ͬ����ʾ��ͬ���ı߿�
	 * @param userType �û����ͣ���ͨ�û������Ա��
	 */
	private static void showBound(String userType)
	{
		for(int i=0;i<("admin".equals(userType)?90:80);i++)
			System.out.print("=");
		System.out.println();
	}
	
	/**
	 * �����û����͵Ĳ�ͬ����ʾ��ͬ���͵�������Ϣ��ͷ��
	 * @param userType �û����ͣ���ͨ�û������Ա��
	 */
	private static void showCarLabel(String userType)
	{
		System.out.print("���\t");
		System.out.print("��������\t");
		System.out.print("��ע\t");
		System.out.print("Ʒ��\t");
		System.out.print("����\t\t");
		System.out.print("���\t\t");
		System.out.print("�Ƿ����\t\t");
		if("admin".equals(userType))
			System.out.print("�Ƿ��ϼ�");
		System.out.println();
	}
	
	/**
	 * �����û����͵Ĳ�ͬ����ӡ����ͬ���͵�������Ϣ�Ľ��������Ϣ��
	 * @param rs ���޼�¼�����
	 * @param userType �û�����
	 * @throws SQLException 
	 */
	private static void showCarInfo(ResultSet rs,String userType) throws SQLException
	{
		System.out.print(rs.getLong("���")+"\t");
		System.out.print(rs.getString("����")+"\t");
		System.out.print(rs.getString("��ע")+"\t");
		System.out.print(rs.getString("Ʒ��")+"("+rs.getLong("Ʒ�Ʊ��")+")\t");
		System.out.print(rs.getString("����")+"("+rs.getLong("���ͱ��")+")\t");
		if("SUV".equals(rs.getString("����")))
			System.out.print("\t");
		System.out.print(rs.getDouble("���")+"/��\t\t");
		System.out.print(rs.getInt("�Ƿ����")==0?"��":"��");
		if("admin".equals(userType))
			System.out.print("\t\t"+(rs.getInt("�Ƿ��ϼ�")==0?"��":"��"));
		System.out.println();
	}
}
