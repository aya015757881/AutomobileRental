package com.oaec.view;

public class CommandView
{
	/**
	 * 根据用户类型，显示相应的操作内容信息。
	 * @param userType 表示用户类型的字符串
	 */
	public static void showOptions(String userType)
	{
		if("user".equals(userType))
		{
			System.out.println("输入0退出");
			System.out.println("输入1+汽车编号  进入租车订单  如1+2");
			System.out.println("输入2+1  按租金降序排序");
			System.out.println("输入2+2  按租金升序排序");
			System.out.println("输入3+类型编号  按类型搜索");
			System.out.println("输入4+品牌编号  按品牌搜索");
			System.out.println("输入5  查看全部汽车");
			System.out.println("输入6  查看我的租车记录");
			System.out.println("输入7+汽车编号  还车");
		}
		else
		{
			System.out.println("输入0退出");
			System.out.println("输入1+汽车编号  查看指定汽车信息");
			System.out.println("输入2  查看全部汽车信息");
			System.out.println("输入3+汽车编号  修改指定汽车信息");
			System.out.println("输入4+汽车编号  删除指定汽车");
			System.out.println("输入5  添加一辆新汽车");
			System.out.println("输入6  查看全部租赁记录");
			System.out.println("输入7+汽车编号  查看指定汽车租赁记录");
			System.out.println("输入8  查看指定用户租赁记录");
		}
	}
}
