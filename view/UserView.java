package com.oaec.view;

import java.sql.SQLException;

import com.oaec.biz.CarDisplayer;
import com.oaec.biz.Checker;
import com.oaec.biz.Getter;
import com.oaec.biz.RecordDisplayer;
import com.oaec.tool.Keyboard;

public class UserView
{
	/**
	 * 普通用户的操作界面（功能选择界面）。
	 * @param username 进行操作的用户的用户名
	 * @throws SQLException
	 */
	public static void showView(String username) throws SQLException
	{
		boolean flag = true;
		CarDisplayer.showAll("user");
		do{
			CommandView.showOptions("user");
			String command = Keyboard.next();
			switch(command)
			{
			case "0":
				flag = false;
				break;
			case "2+1":
				CarDisplayer.showByRent("desc");
				break;
			case "2+2":
				CarDisplayer.showByRent("asc");
				break;
			case "5":
				CarDisplayer.showAll("user");
				break;
			case "6":
				RecordDisplayer.showUserRecord(username);
				break;
			default:
				if(Checker.isLegalCommand(command))
				{
					int id = Getter.getNumber(command);
					switch(command.charAt(0))
					{
					case '1':
						RentView.showView(username,id);
						break;
					case '3':
						CarDisplayer.showById(id,"category");
						break;
					case '4':
						CarDisplayer.showById(id,"brand");
						break;
					case '7':
						ReturnView.showView(username,id);
					}
				}
				else
					System.out.println("无效指令\n");
			}
		}while(flag);
	}
}
