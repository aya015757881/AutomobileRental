package com.oaec.view;

import java.sql.SQLException;
import com.oaec.tool.Keyboard;

public class OptionViewForUser
{
	/**
	 * ��ʾ��ͨ�û���Ԥ��¼���档
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
				LoginView.showView("user");
				break;
			case "2":
				RegisterView.showView();
				break;
			case "3":
				flag = false;
				break;
			default:
				System.out.println("��Чָ��\n");
			}
		}while(flag);
	}
	
	/**
	 * ��ʾ��ͨ�û���¼��������ֱ߿�
	 */
	static void startView()
	{
		for(int i=0;i<30;i++)
			System.out.print("=");
		System.out.println();
		for(int i=0;i<12;i++)
			System.out.print(" ");
		System.out.println("��ӭ���ʶ����⳵����ͨ�û���");
		for(int i=0;i<30;i++)
			System.out.print("=");
		System.out.println("\n\n1.��¼	2.ע��	3.�˳�");
	}
}
