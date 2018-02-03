package com.oaec.biz;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.dao.DBOperator;

public class CarDisplayer
{
	static String userItems = "select c.id 编号,c.model 名称,c.t_comments 备注,bra.name 品牌,"
			+ "bra.id 品牌编号,cat.name 类型,cat.id 类型编号,c.rent 租金,c.status 是否可租 ";

	static String adminItems = userItems + ",c.useable 是否上架 ";
	
	static String rest = "from t_car c,t_brand bra,t_category cat where "
			+ "c.brand_id = bra.id and c.category_id = cat.id ";
	
	static String sqlU = userItems + rest + "and c.useable = 0 ";
	
	static String sqlA = adminItems + rest;
	
	static String SQL;
	
	static ResultSet rs;
	
	/**
	 * 查询出每一辆汽车的信息。
	 * 管理员查询的信息比用户查询的信息多出一个项目，即上下架状态，其余的项目都相同。
	 * @param userType 用户类型（普通用户或管理员）
	 * @throws SQLException
	 */
	public static void showAll(String userType) throws SQLException
	{
		if("user".equals(userType))
			SQL = sqlU + "order by c.id asc";
		else
			SQL = sqlA + "order by c.id asc";
		rs = DBOperator.getResult(SQL);
		if(rs.next())
		{
			System.out.println("全部汽车信息如下：");
			showCars(rs,userType);
		}
		else
			System.out.println("当前没有汽车信息\n");
	}
	
	/**
	 * 查询指定编号的汽车的信息。
	 * @param carId 汽车的编号
	 * @throws SQLException
	 */
	public static void showByCarId(int carId) throws SQLException
	{
		SQL = sqlA + "and c.id = ?";
		rs = DBOperator.getResult(SQL,carId);
		if(rs.next())
		{
			System.out.println(Getter.getCarName(carId)+"的信息如下：");
			showCars(rs,"admin");
		}
		else
			System.out.println("查无此车\n");
	}
	
	/**
	 * 查询指定车牌号的汽车的信息。
	 * @param carNumber 汽车的车牌号
	 * @throws SQLException
	 */
	public static void showByCarNumber(String carNumber) throws SQLException
	{
		SQL = sqlA + "and c.car_number = ?";
		rs = DBOperator.getResult(SQL,carNumber);
		if(rs.next())
			showCars(rs,"admin");
	}
	
	/**
	 * 根据租金的高低降序或升序排列全部上架汽车的信息
	 * @param order 指定升降序的字符串
	 * @throws SQLException
	 */
	public static void showByRent(String order) throws SQLException
	{
		SQL = sqlU + "order by c.rent " + order;
		rs = DBOperator.getResult(SQL);
		if(rs.next())
			showCars(rs,"user");
		else
			System.out.println("当前没有汽车信息\n");
	}
	
	/**
	 * 根据指定的类型ID或品牌ID，查询出该类型或品牌的汽车的信息。
	 * @param id 品牌ID或类型ID
	 * @param type 指定按品牌还是按类型查询的标记字符串
	 * @throws SQLException
	 */
	public static void showById(int id,String type) throws SQLException
	{
		String append = "brand".equals(type)?"and c.brand_id = ?":"and c.category_id = ?";
		SQL = sqlU + append;
		rs = DBOperator.getResult(SQL,id);
		if(rs.next())
			showCars(rs,"user");
		else
			System.out.println("当前没有汽车信息\n");
	}
	
	/**
	 * 根据用户类型的不同，显示不同类别的汽车信息的结果集。
	 * @param rs 汽车信息结果集
	 * @param userType 用户类型（普通用户或管理员）
	 * @throws SQLException
	 */
	private static void showCars(ResultSet rs,String userType) throws SQLException
	{
		showBound(userType);
		showCarLabel(userType);
		showCarInfo(rs,userType);
		while(rs.next())
			showCarInfo(rs,userType);
		System.out.println();
	}
	
	/**
	 * 根据用户类型的不同，显示不同类别的边框。
	 * @param userType 用户类型（普通用户或管理员）
	 */
	private static void showBound(String userType)
	{
		for(int i=0;i<("admin".equals(userType)?90:80);i++)
			System.out.print("=");
		System.out.println();
	}
	
	/**
	 * 根据用户类型的不同，显示不同类型的汽车信息表头。
	 * @param userType 用户类型（普通用户或管理员）
	 */
	private static void showCarLabel(String userType)
	{
		System.out.print("编号\t");
		System.out.print("汽车名称\t");
		System.out.print("备注\t");
		System.out.print("品牌\t");
		System.out.print("类型\t\t");
		System.out.print("租金\t\t");
		System.out.print("是否可租\t\t");
		if("admin".equals(userType))
			System.out.print("是否上架");
		System.out.println();
	}
	
	/**
	 * 根据用户类型的不同，打印出不同类型的汽车信息的结果集的信息。
	 * @param rs 租赁记录结果集
	 * @param userType 用户类型
	 * @throws SQLException 
	 */
	private static void showCarInfo(ResultSet rs,String userType) throws SQLException
	{
		System.out.print(rs.getLong("编号")+"\t");
		System.out.print(rs.getString("名称")+"\t");
		System.out.print(rs.getString("备注")+"\t");
		System.out.print(rs.getString("品牌")+"("+rs.getLong("品牌编号")+")\t");
		System.out.print(rs.getString("类型")+"("+rs.getLong("类型编号")+")\t");
		if("SUV".equals(rs.getString("类型")))
			System.out.print("\t");
		System.out.print(rs.getDouble("租金")+"/天\t\t");
		System.out.print(rs.getInt("是否可租")==0?"是":"否");
		if("admin".equals(userType))
			System.out.print("\t\t"+(rs.getInt("是否上架")==0?"是":"否"));
		System.out.println();
	}
}
