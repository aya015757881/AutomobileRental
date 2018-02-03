package com.oaec.view;

import java.sql.SQLException;

import com.oaec.tool.Keyboard;

public class OptionViewForAdmin
{
	/**
	 * 显示管理员的预登录界面。
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
				System.out.println("无效指令\n");
			}
		}while(flag);
	}
	
	/**
	 * 显示管理员登录界面的有字边框。
	 */
	static void startView()
	{
		for(int i=0;i<30;i++)
			System.out.print("=");
		System.out.println();
		for(int i=0;i<12;i++)
			System.out.print(" ");
		System.out.println("欢迎访问二嗨租车（管理员）");
		for(int i=0;i<30;i++)
			System.out.print("=");
		System.out.println("\n\n1.登录	2.退出");
	}
}
