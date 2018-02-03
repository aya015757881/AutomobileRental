package com.oaec.view;

import java.sql.SQLException;

import com.oaec.biz.Checker;
import com.oaec.tool.*;

public class LoginView
{
	/**
	 * 根据用户类型，显示相应的登录界面。
	 * @param userType 用户类型（普通用户或管理员）
	 * @throws SQLException
	 */
	public static void showView(String userType) throws SQLException
	{
		boolean flag = true;
		boolean isUser = "user".equals(userType)?true:false;
		do{
			startView();
			System.out.print("用户名：");
			String username = Keyboard.next();
			if(isUser?!Checker.userNameExists(username,"login"):!"admin".equals(username))
			{
				if(isUser)
					System.out.println("用户名"+username+"不存在，请输入已注册的用户名\n");
				else
					System.out.println("用户名"+username+"不存在，请输入正确的管理员用户名\n");
				flag = loginContinue();
			}
			else
			{
				System.out.print("密码：");
				String password = Keyboard.next();
				if(isUser?!Checker.userPasswordMatches(username, password):!"code".equals(password))
				{
					System.out.println("密码不正确，请输入正确的密码\n");
					flag = loginContinue();
				}
				else
				{
					for(int i=0;i<20;i++)
						System.out.print("=");
					System.out.println("\n欢迎"+(isUser?"":"管理员")+username+"\n");
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
	 * 指令输入不合法时，显示提示界面。
	 * @return
	 */
	private static boolean loginContinue()
	{
		String command;
		do{
			System.out.println("1.继续登录 2.退出登录界面");
			command = Keyboard.next();
			if("1".equals(command))
				return true;
			else if("2".equals(command))
				return false;
			else
				System.out.println("无效指令");
		}while(true);
	}
	
	/**
	 * 显示登录界面的有字边框。
	 */
	private static void startView()
	{
		System.out.println();
		for(int i=0;i<6;i++)
			System.out.print("=");
		System.out.print("登录");
		for(int i=0;i<6;i++)
			System.out.print("=");
		for(int i=0;i<4;i++)
			System.out.print(">");
		System.out.println();
	}
}
