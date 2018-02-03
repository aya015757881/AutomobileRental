package com.oaec.biz;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.dao.DBOperator;
import com.oaec.tool.Keyboard;

public class RecordDisplayer
{
	static String userItems = "select r.id 租赁编号,r.car_id 汽车编号,c.model 汽车名称,c.rent 每日租金,"
			+ "r.payment 总租金,c.t_comments 备注,bra.name 品牌,cat.name 类型,"
			+ "r.start_date 租车时间,r.return_date 还车时间  ";
	
	static String adminItems = "select r.id 租赁编号,r.car_id 汽车编号,c.model 汽车名称,u.id 用户编号,u.username 用户名,"
			+ "c.rent 每日租金,r.payment 总租金,c.t_comments 备注,bra.name 品牌,cat.name 类型,"
			+ "r.start_date 租车时间,r.return_date 还车时间  ";
	
	static String rest = "from t_record r,t_car c,t_brand bra,t_category cat,t_user u where "
			+ "c.brand_id = bra.id and c.category_id = cat.id and u.id = r.user_id and r.car_id = c.id ";
	
	static String sqlU = userItems + rest + "and u.username = ? ";
	
	static String sqlA = adminItems + rest;
	
	static String SQL;
	
	static ResultSet rs;
	
	/**
	 * 管理员查询全部租赁记录。
	 * @throws SQLException
	 */
	public static void showAll() throws SQLException
	{
		SQL = sqlA + "order by r.id";
		rs = DBOperator.getResult(SQL);
		if(rs.next())
		{
			System.out.println("全部租赁记录如下：");
			showRecords(rs,"admin");
		}
		else
			System.out.println("当前没有租赁记录\n");
	}
	
	/**
	 * 管理员根据指定的汽车编号，查询该编号对应的汽车的租赁记录。
	 * @param carId 汽车的编号
	 * @throws SQLException
	 */
	public static void showByCarId(int carId) throws SQLException
	{
		SQL = sqlA + "and r.car_id = ?";
		rs = DBOperator.getResult(SQL,carId);
		String carName = Getter.getCarName(carId);
		if("".equals(carName))
			System.out.println("查无此车\n");
		else if(rs.next())
		{
			System.out.println(carName+"的所有租赁记录如下：");
			showRecords(rs,"admin");
		}
		else
			System.out.println(carName+"当前没有租赁记录\n");
	}
	
	/**
	 * 管理员根据指定的用户名，查询该用户的租赁记录。
	 * @throws SQLException
	 */
	public static void showByUser() throws SQLException
	{
		System.out.println("请输入指定用户的用户名：");
		String username = Keyboard.next();
		SQL = sqlA + "and u.username = ?";
		if(Getter.getUserId(username)>0)
		{
			rs = DBOperator.getResult(SQL,username);
			if(rs.next())
			{
				System.out.println(username+"的租赁记录如下：");
				showRecords(rs,"admin");
			}
			else
				System.out.println(username+"当前没有租赁记录\n");
		}
		else
			System.out.println("查无此用户\n");
	}
	
	/**
	 * 用户查询自己的租赁记录。
	 * @param username 用户名
	 * @throws SQLException
	 */
	public static void showUserRecord(String username) throws SQLException
	{
		rs = DBOperator.getResult(sqlU,username);
		if(rs.next())
		{
			System.out.println(username+"的租车记录如下：");
			showRecords(rs,"user");
		}
		else
			System.out.println(username+"当前没有租车记录\n");
	}
	
	/**
	 * 显示用户刚刚租的那辆汽车的租赁记录。
	 * @param username 租车的用户的用户名
	 * @param carId 被租汽车的编号
	 * @throws SQLException
	 */
	public static void showRentRecord(String username,int carId) throws SQLException
	{
		SQL = sqlU + "and r.car_id = ? and r.payment = 0";
		rs = DBOperator.getResult(SQL,username,carId);
		if(rs.next())
			showRecords(rs,"user");
	}
	
	/**
	 * 显示用户刚刚还的那辆汽车的租赁记录。
	 * @param username 还车的用户的用户名
	 * @param recordId 被还汽车的编号
	 * @throws SQLException
	 */
	public static void showReturnRecord(String username,int recordId) throws SQLException
	{
		SQL = sqlU + "and r.id = ?";
		rs = DBOperator.getResult(SQL,username,recordId);
		if(rs.next())
			showRecords(rs,"user");
	}
	
	/**
	 * 根据用户类型的不同，显示不同类别的租赁记录结果集。
	 * @param rs 租赁记录结果集
	 * @param userType 用户类型（普通用户或管理员）
	 * @throws SQLException
	 */
	private static void showRecords(ResultSet rs,String userType) throws SQLException
	{
		showBound(userType);
		showRecordLabel(userType);
		showRecordContent(rs,userType);
		while(rs.next())
			showRecordContent(rs,userType);
		System.out.println();
	}
	
	/**
	 * 根据用户类型的不同，显示不同类别的边框。
	 * @param userType 用户类型（普通用户或管理员）
	 */
	private static void showBound(String userType)
	{
		for(int i=0;i<("admin".equals(userType)?110:90);i++)
			System.out.print("=");
		System.out.println();
	}
	
	/**
	 * 根据用户类型的不同，显示不同类型的租赁记录表头。
	 * @param userType 用户类型（普通用户或管理员）
	 */
	private static void showRecordLabel(String userType)
	{
		System.out.print("租赁编号\t");
		System.out.print("汽车编号\t");
		System.out.print("汽车名称\t");
		if("admin".equals(userType))
		{
			System.out.print("用户编号\t");
			System.out.print("用户名\t");
		}
		System.out.print("每日租金\t");
		System.out.print("总租金\t");
		System.out.print("备注\t");
		System.out.print("品牌\t");
		System.out.print("类型\t");
		System.out.print("租车时间\t\t");
		System.out.println("还车时间");
	}
	
	/**
	 * 根据用户类型的不同，打印出不同类型的租赁记录结果集的信息。
	 * @param rs 租赁记录结果集
	 * @param userType 用户类型
	 * @throws SQLException 
	 */
	private static void showRecordContent(ResultSet rs,String userType) throws SQLException
	{
		System.out.print(rs.getLong("租赁编号")+"\t");
		System.out.print(rs.getLong("汽车编号")+"\t");
		System.out.print(rs.getString("汽车名称")+"\t");
		if("admin".equals(userType))
		{
			System.out.print(rs.getInt("用户编号")+"\t");
			System.out.print(rs.getString("用户名")+"\t");
		}
		System.out.print(rs.getDouble("每日租金")+"\t");
		System.out.print(rs.getDouble("总租金")==0?"未付\t":rs.getDouble("总租金")+"\t");
		System.out.print(rs.getString("备注")+"\t");
		System.out.print(rs.getString("品牌")+"\t");
		System.out.print(rs.getString("类型")+"\t");
		System.out.print(rs.getDate("租车时间")+"\t");
		System.out.println(rs.getDate("还车时间")==null?"未还":rs.getDate("还车时间"));
	}
}
