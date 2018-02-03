package com.oaec.view;

import java.sql.SQLException;


import com.oaec.tool.DBHelper;
import com.oaec.tool.Keyboard;

public class MainView
{
	/**
	 * ��ʾ�����⳵ϵͳ�����档
	 * @param args main�����Ĳ���
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		boolean flag = true;
		DBHelper.getConnected();
		do{
			startView();
			switch(Keyboard.next())
			{
			case "1":
				OptionViewForUser.showView();
				break;
			case "2":
				OptionViewForAdmin.showView();
				break;
			case "3":
				flag = false;
				System.out.println("�����˳������⳵");
				break;
			default:
				System.out.println("��Чָ��\n");
			}
		}while(flag);
	}
	
	/**
	 * ��ʾ����������ֱ߿�
	 */
	static void startView()
	{
		for(int i=0;i<30;i++)
			System.out.print("=");
		System.out.println();
		for(int i=0;i<12;i++)
			System.out.print(" ");
		System.out.println("��ӭ���ʶ����⳵");
		for(int i=0;i<30;i++)
			System.out.print("=");
		System.out.println("\n\n1.��ͨ�û�	2.����Ա		3.�˳�");
	}
}
