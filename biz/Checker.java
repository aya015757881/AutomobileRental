package com.oaec.biz;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.oaec.dao.DBOperator;

public class Checker
{
	/**
	 * 判断输入的指令是否合法。
	 * @param command 输入的指令
	 * @return 合法返回true，不合法返回false
	 */
	public static boolean isLegalCommand(String command)
	{
		char arr[] = command.toCharArray();
		if(arr.length<3)
			return false;
		else if(arr[0]!='1'&&arr[0]!='3'&&arr[0]!='4'&&arr[0]!='7')
			return false;
		else if(arr[1]!='+')
			return false;
		else if(!isNumber(arr))
			return false;
		return true;
	}
	
	/**
	 * 判断删除与否的选项编号是否合法
	 * @param command 选项编号
	 * @return 合法返回true，不合法返回false
	 */
	public static boolean isLegalCommand1(String command)
	{
		if("1".equals(command)||"2".equals(command))
			return true;
		else
			return false;
	}
	
	/**
	 * 判断信息选择的选项编号是否合法。
	 * @param input 输入的选项编号
	 * @param type 选项所对应的信息的类别
	 * @return 合法返回true，不合法返回false
	 */
	public static boolean isLegalInput(int input,String type)
	{
		if("brand".equals(type))
			return (input>=1&&input<=5)?true:false;
		else if("category".equals(type))
			return (input>=1&&input<=4)?true:false;
		else
			return (input==0||input==1)?true:false;
	}
	
	/**
	 * 判断指令数组是否为纯数字。
	 * @param arr 指令数组
	 * @return 是纯数字返回true，否则返回false
	 */
	public static boolean isNumber(char arr[])
	{
		for(int i=2;i<arr.length;i++)
			if(arr[i]>'9'||arr[i]<(i==2?'1':'0'))
				return false;
		return true;
	}
	
	/**
	 * 判断输入的指令是否为纯数字。
	 * @param str 输入的指令
	 * @return 指令为纯数字返回true，否则返回false
	 */
	public static boolean isNumber(String str)
	{
		char arr[] = str.toCharArray();
		for(int i=0;i<arr.length;i++)
			if(arr[i]>'9'||arr[i]<(i==0?'1':'0'))
				return false;
		return true;
	}
	
	/**
	 * 判断给定的用户名在数据库用户表中是否存在。
	 * @param username 给定的用户名
	 * @return 存在返回true，否则返回false
	 * @throws SQLException
	 */
	public static boolean userNameExists(String username,String type) throws SQLException
	{
		ResultSet rs = DBOperator.getResult("select username from t_user where username = ?",username);
		if(rs.next())
		{
			if(!"login".equals(type))
				System.out.println("用户名已存在，请更换用户名\n");
			return true;
		}
		return false;
	}
	
	/**
	 * 判断给定的密码与给定的用户名在数据库的用户表中是否属于同一个用户，即是否匹配。
	 * @param username 给定的用户名
	 * @param password 给定的密码
	 * @return 匹配返回true，否则返回false
	 * @throws SQLException
	 */
	public static boolean userPasswordMatches(String username,String password) throws SQLException
	{
		ResultSet rs = DBOperator.getResult("select id from t_user where username = ? and password = ?",
				username,password);
		if(rs.next())
			return true;
		return false;
	}
	
	/**
	 * 判断给定编号的汽车在数据库汽车信息表中是否存在。
	 * @param carId 给定的汽车编号
	 * @return 存在返回true 否则返回false
	 * @throws SQLException
	 */
	public static boolean carExists(int carId) throws SQLException
	{
		ResultSet rs = DBOperator.getResult("select id from t_car where id = ?",carId);
		return rs.next();
	}
	
	/**
	 * 判断给定的身份证号码在数据库的用户表中是否存在。
	 * @param idNumber 给定的身份证号
	 * @return 存在返回true，否则返回false
	 * @throws SQLException
	 */
	public static boolean idNumberExists(String idNumber) throws SQLException
	{
		ResultSet rs = DBOperator.getResult("select id_number from t_user where id_number = ?",idNumber);
		if(rs.next())
			return true;
		return false;
	}
	
	/**
	 * 判断给定的车牌号在数据库的汽车信息表中是否存在。
	 * @param carNumber 给定的车牌号
	 * @return 存在返回true，否则返回false
	 * @throws SQLException
	 */
	public static boolean carNumberExists(String carNumber) throws SQLException
	{
		ResultSet rs = DBOperator.getResult("select car_number from t_car where car_number = ?",carNumber);
		if(rs.next())
		{
			System.out.println("该车牌号已存在，请更换车牌号\n");
			return true;
		}
		return false;
	}
	
	/**
	 * 判断给定编号的汽车是否处于已出租状态。
	 * @param carId 给定的汽车编号
	 * @return 若已出租，返回true，否则返回false
	 * @throws SQLException
	 */
	public static boolean carRented(int carId) throws SQLException
	{
		ResultSet rs = DBOperator.getResult("select status from t_car where id = ?",carId);
		rs.next();
		if(rs.getInt(1)==1)
			return true;
		return false;
	}
	
	/**
	 * 判断密码是否不小于6位字符。
	 * @param password 接收到的密码字符串
	 * @return 不小于6位字符返回true，否则返回false
	 */
	public static boolean isLegalPassword(String password)
	{
		if(password.length()<6)
		{
			System.out.println("密码不能少于6位字符，请重新输入密码\n");
			return false;
		}
		return true;
	}
	
	/**
	 * 判断性别输入是否合法。
	 * @param sex 性别输入
	 * @return 合法返回true，不合法返回false
	 */
	public static boolean isLegalSex(String sex)
	{
		if("0".equals(sex)||"1".equals(sex))
			return true;
		else
		{
			System.out.println("输入不合法，请输入0（男）或1（女）\n");
			return false;
		}
	}
	
	/**
	 * 判断身份证号是否合法。
	 * @param id 身份证号字符串
	 * @return 合法返回true，不合法返回false
	 * @throws SQLException 
	 */
	public static boolean isLegalId(String id) throws SQLException
	{
		if(id.length()!=18)
		{
			System.out.println("身份证号必须为18位，请重新输入\n");
			return false;
		}
		else
		{
			char idNum[] = id.toCharArray();
			for(int i=0;i<idNum.length-1;i++)
				if(idNum[i]<'0'||idNum[i]>'9')
				{
					System.out.println("身份证号前17位必须为数字，请重新输入\n");
					return false;
				}
			if(!(idNum[17]>='0'&&idNum[17]<='9'||idNum[17]=='x'||idNum[17]=='X'))
			{
				System.out.println("身份证号最后一位必须为数字或字母x，请重新输入\n");
				return false;
			}
			ResultSet rs = DBOperator.getResult("select id_number from t_user where id_number = ?",id);
			if(rs.next())
			{
				System.out.println("该身份证已被注册，请重新输入\n");
				return false;
			}
			return true;
		}
	}
	
	/**
	 * 判断手机号码是否合法。
	 * @param tel 手机号码字符串 
	 * @return 合法返回true，不合法返回false
	 */
	public static boolean isLegalTel(String tel)
	{
		char telArr[] = tel.toCharArray();
		for(int i=0;i<telArr.length;i++)
			if(telArr[i]<'0'||telArr[i]>'9')
			{
				System.out.println("手机号码必须为数字，请重新输入\n");
				return false;
			}
		if(tel.length()<11)
		{
			System.out.println("手机号码不能低于11位数字，请重新输入\n");
			return false;
		}
		return true;
	}
	
	/**
	 * 判断选项输入的选项号码是否合法。
	 * @param command 输入的选项号码字符串
	 * @param type 选项号码所选择的内容类型
	 * @return
	 */
	public static boolean isLegalStringOfOptionIndex(String command,String type)
	{
		boolean flag = false;
		if(command.length()==1)
		{
			char commandChar = command.charAt(0);
			switch(type)
			{
			case "brand":
			case "category":
				char bound = "brand".equals(type)?'5':'4';
				if(commandChar>='1'&&commandChar<=bound)
					flag = true;
				break;
			case "status":
			case "useable":
				if(commandChar=='0'||commandChar=='1')
					flag = true;
			}
		}
		if(!flag)
			System.out.println("输入不合法，请重新输入\n");
		return flag;
	}
	
	/**
	 * 判断输入是否为一个浮点数。
	 * @param num 输入的浮点数字符串
	 * @return 是浮点数返回true，否则返回false
	 */
	public static boolean isLegalDouble(String num)
	{
		boolean flag = true;
		char numArr[] = num.toCharArray();
		for(int i=0;i<numArr.length;i++)
			if(!(numArr[i]>='0'&&numArr[i]<='9'||numArr[i]=='.'))
				flag = false;
		if(numArr[0]=='.'||numArr[numArr.length-1]=='.')
			flag = false;
		if(!flag)
			System.out.println("输入不合法，请重新输入\n");
		return flag;
	}
}
