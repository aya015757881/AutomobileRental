package com.oaec.view;

import java.sql.SQLException;

import com.oaec.biz.CarDisplayer;
import com.oaec.biz.Checker;
import com.oaec.biz.Getter;
import com.oaec.biz.RecordDisplayer;
import com.oaec.biz.Updator;
import com.oaec.tool.Keyboard;

public class AdminView
{
	/**
	 * 显示管理员的操作界面。
	 * @throws SQLException
	 */
	public static void showView() throws SQLException
	{
		boolean flag = true;
		CarDisplayer.showAll("admin");
		do{
			CommandView.showOptions("admin");
			String command = Keyboard.next();
			switch(command)
			{
			case "0":
				flag = false;
				break;
			case "2":
				CarDisplayer.showAll("admin");
				break;
			case "5":
				Updator.addCar();
				break;
			case "6":
				RecordDisplayer.showAll();
				break;
			case "8":
				RecordDisplayer.showByUser();
				break;
			default:
				if(Checker.isLegalCommand(command))
				{
					int id = Getter.getNumber(command);
					switch(command.charAt(0))
					{
					case '1':
						CarDisplayer.showByCarId(id);
						break;
					case '3':
						Updator.updateCarInfo(id);
						break;
					case '4':
						Updator.removeCar(id);
						break;
					case '7':
						RecordDisplayer.showByCarId(id);
					}
				}
				else
					System.out.println("无效指令\n");
			}
		}while(flag);
	}
}
