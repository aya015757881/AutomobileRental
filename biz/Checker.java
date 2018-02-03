package com.oaec.biz;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.oaec.dao.DBOperator;

public class Checker
{
	/**
	 * �ж������ָ���Ƿ�Ϸ���
	 * @param command �����ָ��
	 * @return �Ϸ�����true�����Ϸ�����false
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
	 * �ж�ɾ������ѡ�����Ƿ�Ϸ�
	 * @param command ѡ����
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public static boolean isLegalCommand1(String command)
	{
		if("1".equals(command)||"2".equals(command))
			return true;
		else
			return false;
	}
	
	/**
	 * �ж���Ϣѡ���ѡ�����Ƿ�Ϸ���
	 * @param input �����ѡ����
	 * @param type ѡ������Ӧ����Ϣ�����
	 * @return �Ϸ�����true�����Ϸ�����false
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
	 * �ж�ָ�������Ƿ�Ϊ�����֡�
	 * @param arr ָ������
	 * @return �Ǵ����ַ���true�����򷵻�false
	 */
	public static boolean isNumber(char arr[])
	{
		for(int i=2;i<arr.length;i++)
			if(arr[i]>'9'||arr[i]<(i==2?'1':'0'))
				return false;
		return true;
	}
	
	/**
	 * �ж������ָ���Ƿ�Ϊ�����֡�
	 * @param str �����ָ��
	 * @return ָ��Ϊ�����ַ���true�����򷵻�false
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
	 * �жϸ������û��������ݿ��û������Ƿ���ڡ�
	 * @param username �������û���
	 * @return ���ڷ���true�����򷵻�false
	 * @throws SQLException
	 */
	public static boolean userNameExists(String username,String type) throws SQLException
	{
		ResultSet rs = DBOperator.getResult("select username from t_user where username = ?",username);
		if(rs.next())
		{
			if(!"login".equals(type))
				System.out.println("�û����Ѵ��ڣ�������û���\n");
			return true;
		}
		return false;
	}
	
	/**
	 * �жϸ�����������������û��������ݿ���û������Ƿ�����ͬһ���û������Ƿ�ƥ�䡣
	 * @param username �������û���
	 * @param password ����������
	 * @return ƥ�䷵��true�����򷵻�false
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
	 * �жϸ�����ŵ����������ݿ�������Ϣ�����Ƿ���ڡ�
	 * @param carId �������������
	 * @return ���ڷ���true ���򷵻�false
	 * @throws SQLException
	 */
	public static boolean carExists(int carId) throws SQLException
	{
		ResultSet rs = DBOperator.getResult("select id from t_car where id = ?",carId);
		return rs.next();
	}
	
	/**
	 * �жϸ��������֤���������ݿ���û������Ƿ���ڡ�
	 * @param idNumber ���������֤��
	 * @return ���ڷ���true�����򷵻�false
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
	 * �жϸ����ĳ��ƺ������ݿ��������Ϣ�����Ƿ���ڡ�
	 * @param carNumber �����ĳ��ƺ�
	 * @return ���ڷ���true�����򷵻�false
	 * @throws SQLException
	 */
	public static boolean carNumberExists(String carNumber) throws SQLException
	{
		ResultSet rs = DBOperator.getResult("select car_number from t_car where car_number = ?",carNumber);
		if(rs.next())
		{
			System.out.println("�ó��ƺ��Ѵ��ڣ���������ƺ�\n");
			return true;
		}
		return false;
	}
	
	/**
	 * �жϸ�����ŵ������Ƿ����ѳ���״̬��
	 * @param carId �������������
	 * @return ���ѳ��⣬����true�����򷵻�false
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
	 * �ж������Ƿ�С��6λ�ַ���
	 * @param password ���յ��������ַ���
	 * @return ��С��6λ�ַ�����true�����򷵻�false
	 */
	public static boolean isLegalPassword(String password)
	{
		if(password.length()<6)
		{
			System.out.println("���벻������6λ�ַ�����������������\n");
			return false;
		}
		return true;
	}
	
	/**
	 * �ж��Ա������Ƿ�Ϸ���
	 * @param sex �Ա�����
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public static boolean isLegalSex(String sex)
	{
		if("0".equals(sex)||"1".equals(sex))
			return true;
		else
		{
			System.out.println("���벻�Ϸ���������0���У���1��Ů��\n");
			return false;
		}
	}
	
	/**
	 * �ж����֤���Ƿ�Ϸ���
	 * @param id ���֤���ַ���
	 * @return �Ϸ�����true�����Ϸ�����false
	 * @throws SQLException 
	 */
	public static boolean isLegalId(String id) throws SQLException
	{
		if(id.length()!=18)
		{
			System.out.println("���֤�ű���Ϊ18λ������������\n");
			return false;
		}
		else
		{
			char idNum[] = id.toCharArray();
			for(int i=0;i<idNum.length-1;i++)
				if(idNum[i]<'0'||idNum[i]>'9')
				{
					System.out.println("���֤��ǰ17λ����Ϊ���֣�����������\n");
					return false;
				}
			if(!(idNum[17]>='0'&&idNum[17]<='9'||idNum[17]=='x'||idNum[17]=='X'))
			{
				System.out.println("���֤�����һλ����Ϊ���ֻ���ĸx������������\n");
				return false;
			}
			ResultSet rs = DBOperator.getResult("select id_number from t_user where id_number = ?",id);
			if(rs.next())
			{
				System.out.println("�����֤�ѱ�ע�ᣬ����������\n");
				return false;
			}
			return true;
		}
	}
	
	/**
	 * �ж��ֻ������Ƿ�Ϸ���
	 * @param tel �ֻ������ַ��� 
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public static boolean isLegalTel(String tel)
	{
		char telArr[] = tel.toCharArray();
		for(int i=0;i<telArr.length;i++)
			if(telArr[i]<'0'||telArr[i]>'9')
			{
				System.out.println("�ֻ��������Ϊ���֣�����������\n");
				return false;
			}
		if(tel.length()<11)
		{
			System.out.println("�ֻ����벻�ܵ���11λ���֣�����������\n");
			return false;
		}
		return true;
	}
	
	/**
	 * �ж�ѡ�������ѡ������Ƿ�Ϸ���
	 * @param command �����ѡ������ַ���
	 * @param type ѡ�������ѡ�����������
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
			System.out.println("���벻�Ϸ�������������\n");
		return flag;
	}
	
	/**
	 * �ж������Ƿ�Ϊһ����������
	 * @param num ����ĸ������ַ���
	 * @return �Ǹ���������true�����򷵻�false
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
			System.out.println("���벻�Ϸ�������������\n");
		return flag;
	}
}
