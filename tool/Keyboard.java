package com.oaec.tool;

import java.util.Scanner;

public class Keyboard
{
	static Scanner scan = new Scanner(System.in);
	
	/**
	 * �Ӽ��̽���һ��������
	 * @return
	 */
	public static int nextInt()
	{
		return scan.nextInt();
	}
	
	/**
	 * �Ӽ��̽���һ���ַ�����
	 * @return
	 */
	public static String next()
	{
		return scan.next();
	}
	
	/**
	 * �Ӽ��̽���һ����������
	 * @return
	 */
	public static double nextDouble()
	{
		return scan.nextDouble();
	}
}
