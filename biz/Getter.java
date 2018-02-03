package com.oaec.biz;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.dao.DBOperator;

public class Getter
{
	/**
	 * 将纯数字的字符串转换为一个整型数字。
	 * @param numStr 纯数字字符串
	 * @return 返回转化好的整型数字
	 */
	public static int getNumber(String numStr)
	{
		char arr[] = numStr.toCharArray();
		int sum = 0;
		for(int i=arr.length-1,s=1;i>1;i--,s*=10)
			sum+=(arr[i]-'0')*s;
		return sum;
	}
	
	/**
	 * 根据用户名查询用户编号。
	 * @param username 用户名
	 * @return 查询到用户编号则返回编号，没有查询到则返回0
	 * @throws SQLException
	 */
	public static int getUserId(String username) throws SQLException
	{
		ResultSet useridRs = DBOperator.getResult("select id from t_user where username = ?",username);
		if(useridRs.next())
			return useridRs.getInt("id");
		else
			return 0;
	}
	
	/**
	 * 根据指定的汽车编号，查询对应的汽车名称。
	 * @param carId 指定的汽车编号
	 * @return 若找到编号对应的汽车，返回其名称，否则返回空字符串
	 * @throws SQLException
	 */
	public static String getCarName(int carId) throws SQLException
	{
		ResultSet rs = DBOperator.getResult("select model from t_car where id = ?",carId);
		if(rs.next())
			return rs.getString(1);
		return "";
	}
	
	/**
	 * 把输入的表示性别编号的字符串转换为相应的整型数。
	 * @param sex 输入的性别编号字符串
	 * @return 如果是性别编号字符串，则返回相应的整数，否则返回-1
	 */
	public static int getSexIndex(String sex)
	{
		if("1".equals(sex))
			return 1;
		else if("0".equals(sex))
			return 0;
		else
			return -1;
	}
	
	/**
	 * 将传递过来的表示浮点数的字符串转换为真正的浮点数，并返回。
	 * @param num 传递过来的浮点数字符串
	 * @return 返回字符串所表示的浮点数
	 */
	public static double parseDouble(String num)
	{
		double integ = 0, doub = 0;
		char numChar[] = num.toCharArray();
		int pointIndex = indexOfPoint(numChar);
		if(pointIndex==-1)
		{
			for(int i=numChar.length-1,r=1;i>=0;i--,r*=10)
				integ += (numChar[i]-'0')*r;
			return integ;
		}
		else
		{
			for(int i=pointIndex-1,r=1;i>=0;i--,r*=10)
				integ += (numChar[i]-'0')*r;
			double r = 10;
			for(int i=pointIndex+1;i<numChar.length;i++,r*=10)
				doub += (numChar[i]-'0')/r;
			return integ + doub;
		}
	}
	
	/**
	 * 返回浮点数字符数组小数点所在的位置
	 * @param numChar 浮点数字符数组
	 * @return 若有小数点，则返回它在数组中的位置，否则返回-1
	 */
	private static int indexOfPoint(char numChar[])
	{
		for(int i=0;i<numChar.length;i++)
			if(numChar[i]=='.')
				return i;
		return -1;
	}
	
	/**
	 * 从键盘录入的数字是以字符串形式录入的，此方法接收数字字符串，返回对应的数字。
	 * @param option 传递进来的数字字符串 
	 * @return 返回对应的数字
	 */
	public static int optionIndex(String option)
	{
		return option.charAt(0)-'0';
	}
}
