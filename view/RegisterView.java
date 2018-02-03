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
	 * ��ʾ��ͨ�û���ע����档
	 * @throws SQLException
	 */
	public static void showView() throws SQLException
	{
		User user = new User();
		startView();
		System.out.print("�������û�����");
		String username;
		do username = Keyboard.next();
		while(Checker.userNameExists(username,"register"));
		user.setUsername(username);
		System.out.print("�����������룺");
		String password;
		do password = Keyboard.next();
		while(!Checker.isLegalPassword(password));
		user.setPassword(password);
		System.out.print("�������Ա��У�����0��Ů������1����");
		String sex;
		do sex = Keyboard.next();
		while(!Checker.isLegalSex(sex));
		user.setSex(Getter.getSexIndex(sex));
		System.out.print("���������֤�ţ�");
		String id;
		do id = Keyboard.next();
		while(!Checker.isLegalId(id));
		user.setId_number(id);
		System.out.print("�������ֻ��ţ�");
		String tel;
		do tel = Keyboard.next();
		while(!Checker.isLegalTel(tel));
		user.setTel(tel);
		System.out.print("�������ַ��");
		user.setAddr(Keyboard.next());
		if(Updator.addNewUser(user)>0)
		{
			System.out.println("ע��ɹ���\n��ӭ"+username+"\n");
			UserView.showView(username);
		}
		else
			System.out.println("ע��ʧ��!");
	}
	
	/**
	 * ��ʾ��ͨ�û�ע���������ֱ߿�
	 */
	private static void startView()
	{
		System.out.println();
		for(int i=0;i<6;i++)
			System.out.print("=");
		System.out.print("ע��");
		for(int i=0;i<6;i++)
			System.out.print("=");
		for(int i=0;i<4;i++)
			System.out.print(">");
		System.out.println();
	}
}
