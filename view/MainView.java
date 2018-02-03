package com.oaec.view;

import java.sql.SQLException;


import com.oaec.tool.DBHelper;
import com.oaec.tool.Keyboard;

public class MainView
{
	/**
	 * 显示二嗨租车系统主界面。
	 * @param args main方法的参数
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
				System.out.println("您已退出二嗨租车");
				break;
			default:
				System.out.println("无效指令\n");
			}
		}while(flag);
	}
	
	/**
	 * 显示主界面的有字边框。
	 */
	static void startView()
	{
		for(int i=0;i<30;i++)
			System.out.print("=");
		System.out.println();
		for(int i=0;i<12;i++)
			System.out.print(" ");
		System.out.println("欢迎访问二嗨租车");
		for(int i=0;i<30;i++)
			System.out.print("=");
		System.out.println("\n\n1.普通用户	2.管理员		3.退出");
	}
}
