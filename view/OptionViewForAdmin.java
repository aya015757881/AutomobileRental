package com.oaec.view;

import java.sql.SQLException;

import com.oaec.tool.Keyboard;

public class OptionViewForAdmin
{
	/**
	 * ��ʾ����Ա��Ԥ��¼���档
	 * @throws SQLException
	 */
	public static void showView() throws SQLException
	{
		boolean flag = true;
		do{
			startView();
			switch(Keyboard.next())
			{
			case "1":
				LoginView.showView("admin");
				break;
			case "2":
				flag = false;
				break;
			default:
				System.out.println("��Чָ��\n");
			}
		}while(flag);
	}
	
	/**
	 * ��ʾ����Ա��¼��������ֱ߿�
	 */
	static void startView()
	{
		for(int i=0;i<30;i++)
			System.out.print("=");
		System.out.println();
		for(int i=0;i<12;i++)
			System.out.print(" ");
		System.out.println("��ӭ���ʶ����⳵������Ա��");
		for(int i=0;i<30;i++)
			System.out.print("=");
		System.out.println("\n\n1.��¼	2.�˳�");
	}
}
