package com.oaec.view;

import java.sql.SQLException;
import com.oaec.bean.User;
import com.oaec.biz.Checker;
import com.oaec.biz.Getter;
import com.oaec.biz.Updator;
import com.oaec.tool.Keyboard;

public class RegisterView
{
	/**
	 * 显示普通用户的注册界面。
	 * @throws SQLException
	 */
	public static void showView() throws SQLException
	{
		User user = new User();
		startView();
		System.out.print("输入新用户名：");
		String username;
		do username = Keyboard.next();
		while(Checker.userNameExists(username,"register"));
		user.setUsername(username);
		System.out.print("请输入新密码：");
		String password;
		do password = Keyboard.next();
		while(!Checker.isLegalPassword(password));
		user.setPassword(password);
		System.out.print("请输入性别（男：输入0，女：输入1）：");
		String sex;
		do sex = Keyboard.next();
		while(!Checker.isLegalSex(sex));
		user.setSex(Getter.getSexIndex(sex));
		System.out.print("请输入身份证号：");
		String id;
		do id = Keyboard.next();
		while(!Checker.isLegalId(id));
		user.setId_number(id);
		System.out.print("请输入手机号：");
		String tel;
		do tel = Keyboard.next();
		while(!Checker.isLegalTel(tel));
		user.setTel(tel);
		System.out.print("请输入地址：");
		user.setAddr(Keyboard.next());
		if(Updator.addNewUser(user)>0)
		{
			System.out.println("注册成功！\n欢迎"+username+"\n");
			UserView.showView(username);
		}
		else
			System.out.println("注册失败!");
	}
	
	/**
	 * 显示普通用户注册界面的有字边框。
	 */
	private static void startView()
	{
		System.out.println();
		for(int i=0;i<6;i++)
			System.out.print("=");
		System.out.print("注册");
		for(int i=0;i<6;i++)
			System.out.print("=");
		for(int i=0;i<4;i++)
			System.out.print(">");
		System.out.println();
	}
}
