package com.oaec.biz;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.dao.DBOperator;

public class Getter
{
	/**
	 * �������ֵ��ַ���ת��Ϊһ���������֡�
	 * @param numStr �������ַ���
	 * @return ����ת���õ���������
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
	 * �����û�����ѯ�û���š�
	 * @param username �û���
	 * @return ��ѯ���û�����򷵻ر�ţ�û�в�ѯ���򷵻�0
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
	 * ����ָ����������ţ���ѯ��Ӧ���������ơ�
	 * @param carId ָ�����������
	 * @return ���ҵ���Ŷ�Ӧ�����������������ƣ����򷵻ؿ��ַ���
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
	 * ������ı�ʾ�Ա��ŵ��ַ���ת��Ϊ��Ӧ����������
	 * @param sex ������Ա����ַ���
	 * @return ������Ա����ַ������򷵻���Ӧ�����������򷵻�-1
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
	 * �����ݹ����ı�ʾ���������ַ���ת��Ϊ�����ĸ������������ء�
	 * @param num ���ݹ����ĸ������ַ���
	 * @return �����ַ�������ʾ�ĸ�����
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
	 * ���ظ������ַ�����С�������ڵ�λ��
	 * @param numChar �������ַ�����
	 * @return ����С���㣬�򷵻����������е�λ�ã����򷵻�-1
	 */
	private static int indexOfPoint(char numChar[])
	{
		for(int i=0;i<numChar.length;i++)
			if(numChar[i]=='.')
				return i;
		return -1;
	}
	
	/**
	 * �Ӽ���¼������������ַ�����ʽ¼��ģ��˷������������ַ��������ض�Ӧ�����֡�
	 * @param option ���ݽ����������ַ��� 
	 * @return ���ض�Ӧ������
	 */
	public static int optionIndex(String option)
	{
		return option.charAt(0)-'0';
	}
}
