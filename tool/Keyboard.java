package com.oaec.tool;

import java.util.Scanner;

public class Keyboard
{
	static Scanner scan = new Scanner(System.in);
	
	/**
	 * 从键盘接收一个整数。
	 * @return
	 */
	public static int nextInt()
	{
		return scan.nextInt();
	}
	
	/**
	 * 从键盘接收一个字符串。
	 * @return
	 */
	public static String next()
	{
		return scan.next();
	}
	
	/**
	 * 从键盘接收一个浮点数。
	 * @return
	 */
	public static double nextDouble()
	{
		return scan.nextDouble();
	}
}
