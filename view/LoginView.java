package com.oaec.view;

import java.sql.SQLException;

import com.oaec.biz.Checker;
import com.oaec.tool.*;

public class LoginView
{
	/**
	 * �����û����ͣ���ʾ��Ӧ�ĵ�¼���档
	 * @param userType �û����ͣ���ͨ�û������Ա��
	 * @throws SQLException
	 */
	public static void showView(String userType) throws SQLException
	{
		boolean flag = true;
		boolean isUser = "user".equals(userType)?true:false;
		do{
			startView();
			System.out.print("�û�����");
			String username = Keyboard.next();
			if(isUser?!Checker.userNameExists(username,"login"):!"admin".equals(username))
			{
				if(isUser)
					System.out.println("�û���"+username+"�����ڣ���������ע����û���\n");
				else
					System.out.println("�û���"+username+"�����ڣ���������ȷ�Ĺ���Ա�û���\n");
				flag = loginContinue();
			}
			else
			{
				System.out.print("���룺");
				String password = Keyboard.next();
				if(isUser?!Checker.userPasswordMatches(username, password):!"code".equals(password))
				{
					System.out.println("���벻��ȷ����������ȷ������\n");
					flag = loginContinue();
				}
				else
				{
					for(int i=0;i<20;i++)
						System.out.print("=");
					System.out.println("\n��ӭ"+(isUser?"":"����Ա")+username+"\n");
					if(isUser)
						UserView.showView(username);
					else
						AdminView.showView();
					flag = false;
				}
			}
		}while(flag);
	}
	
	/**
	 * ָ�����벻�Ϸ�ʱ����ʾ��ʾ���档
	 * @return
	 */
	private static boolean loginContinue()
	{
		String command;
		do{
			System.out.println("1.������¼ 2.�˳���¼����");
			command = Keyboard.next();
			if("1".equals(command))
				return true;
			else if("2".equals(command))
				return false;
			else
				System.out.println("��Чָ��");
		}while(true);
	}
	
	/**
	 * ��ʾ��¼��������ֱ߿�
	 */
	private static void startView()
	{
		System.out.println();
		for(int i=0;i<6;i++)
			System.out.print("=");
		System.out.print("��¼");
		for(int i=0;i<6;i++)
			System.out.print("=");
		for(int i=0;i<4;i++)
			System.out.print(">");
		System.out.println();
	}
}
