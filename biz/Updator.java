package com.oaec.biz;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.tool.DBHelper;
import com.oaec.dao.DBOperator;
import com.oaec.bean.Car;
import com.oaec.bean.User;
import com.oaec.tool.Keyboard;

public class Updator
{
	/**
	 * 管理员修改指定编号的汽车信息的操作界面。
	 * @param carId 汽车的编号
	 * @throws SQLException
	 */
	public static void updateCarInfo(int carId) throws SQLException
	{
		if(!Checker.carExists(carId))
			System.out.println("查无此车！\n");
		else if(Checker.carRented(carId))
			System.out.println(Getter.getCarName(carId)+"已被出租尚未归还，只有归还后才能修改其信息\n");
		else
		{
			CarDisplayer.showByCarId(carId);
			System.out.println("1.修改租金  2.修改上下架状态");
			switch(Keyboard.next())
			{
			case "1":
				System.out.println("请输入新的租金");
				String rent;
				do rent = Keyboard.next();
				while(!Checker.isLegalDouble(rent));
				if(updateRent(carId,Getter.parseDouble(rent))>0)
					System.out.println("修改成功!");
				else
					System.out.println("修改失败!");
				CarDisplayer.showByCarId(carId);
				break;
			case "2":
				if(updateAvailability(carId)>0)
					System.out.println("修改成功！");
				else
					System.out.println("修改失败！");
				CarDisplayer.showByCarId(carId);
				break;
			default:
				System.out.println("无效指令");
			}
		}
	}
	
	/**
	 * 修改指定编号的汽车的租金。
	 * @param carId 指定的汽车编号
	 * @param rent 目标租金
	 * @return 修改成功返回汽车信息的行数，否则返回0
	 * @throws SQLException
	 */
	private static int updateRent(int carId,double rent) throws SQLException
	{
		return DBOperator.update("update t_car set rent = ? where id = ?",rent,carId);
	}
	
	/**
	 * 修改指定编号的汽车的上下架状态。
	 * @param carId 指定的汽车编号
	 * @return 若修改成功，返回数据库汽车信息表中该汽车所在的行序数，否则返回0
	 * @throws SQLException
	 */
	private static int updateAvailability(int carId) throws SQLException
	{
		ResultSet stateRs = DBOperator.getResult("select useable from t_car where id = ?",carId);
		stateRs.next();
		int state = stateRs.getInt("useable");
		int update;
		if(state == 1)
			update = DBOperator.update("update t_car set useable = 0 where id = ?",carId);
		else
			update = DBOperator.update("update t_car set useable = 1 where id = ?",carId);
		return update;
	}
	
	/**
	 * 管理员添加一辆新汽车的操作界面。
	 * @throws SQLException
	 */
	public static void addCar() throws SQLException
	{
		Car car = new Car();
		System.out.println("请为新车添加以下信息：");
		System.out.println("品牌：1.标致 2.大众 3.奥迪 4.奔驰 5宝马");
		String brandId;
		do brandId = Keyboard.next();
		while(!Checker.isLegalStringOfOptionIndex(brandId,"brand"));
		car.setBrand_id(Getter.optionIndex(brandId));
		System.out.println("类型：1.紧凑型 2.舒适型 3.SUV 4.精英型");
		String categoryId;
		do categoryId = Keyboard.next();
		while(!Checker.isLegalStringOfOptionIndex(categoryId,"category"));
		car.setCategory_id(Getter.optionIndex(categoryId));
		System.out.println("名称：");
		car.setModel(Keyboard.next());
		System.out.println("车牌号：");
		String carNum;
		do carNum = Keyboard.next();
		while(Checker.carNumberExists(carNum));
		car.setCar_number(carNum);
		System.out.println("概要：");
		car.setT_comments(Keyboard.next());
		System.out.println("颜色：");
		car.setColor(Keyboard.next());
		System.out.println("售价：");
		String price;
		do price = Keyboard.next();
		while(!Checker.isLegalDouble(price));
		car.setPrice(Getter.parseDouble(price));
		System.out.println("每日租金：");
		String rent;
		do rent = Keyboard.next();
		while(!Checker.isLegalDouble(rent));
		car.setRent(Getter.parseDouble(rent));
		System.out.println("是否可租： （0.可租  1.不可租）");
		String status;
		do status = Keyboard.next();
		while(!Checker.isLegalStringOfOptionIndex(status,"status"));
		car.setStatus(Getter.optionIndex(status));
		System.out.println("是否上架：（0.上架  1.下架）");
		String useable;
		do useable = Keyboard.next();
		while(!Checker.isLegalStringOfOptionIndex(useable,"useable"));
		car.setUseable(Getter.optionIndex(useable));
		
		if(addNewCar(car)>0)
		{
			System.out.println("添加成功!");
			CarDisplayer.showByCarNumber(car.getCar_number());
		}
		else
			System.out.println("添加失败!");
	}
	
	/**
	 * 管理员删除指定编号的汽车的操作界面。
	 * @param carId 汽车的编号
	 * @throws SQLException
	 */
	public static void removeCar(int carId) throws SQLException
	{
		if(!Checker.carExists(carId))
			System.out.println("查无此车!\n");
		else if(Checker.carRented(carId))
			System.out.println(Getter.getCarName(carId)+"已被出租尚未归还，只有归还后才能把它删除\n");
		else
		{
			System.out.println("确定删除"+Getter.getCarName(carId)+"吗？\n1.确定  2.取消\n");
			CarDisplayer.showByCarId(carId);
			String command;
			if(Checker.isLegalCommand1(command = Keyboard.next()))
			{
				if("1".equals(command))
				{
					if(deleteCar(carId)>0)
						System.out.println("删除成功！\n");
					else
						System.out.println("删除失败！\n");
				}
				else
					System.out.println("删除操作已取消!\n");
			}
			else
				System.out.println("无效指令！\n");
		}
	}
	
	/**
	 * 删除指定编号的汽车。
	 * @param carId 指定的汽车编号
	 * @return 若删除成功，返回数据库汽车信息表中该汽车原先所在的行序数，否则返回0
	 * @throws SQLException
	 */
	private static int deleteCar(int carId) throws SQLException
	{
		int dRec = DBOperator.update("delete from t_record where car_id = ?",carId);
		int dCar = DBOperator.update("delete from t_car where id = ?",carId);
		return dRec>0||dCar>0?1:0;
	}
	
	/**
	 * 往数据库用户表中添加新用户。
	 * @param user 包含新用户注册信息的对象
	 * @return 添加成功返回用户表中新用户所在的行序数，添加失败返回0
	 * @throws SQLException
	 */
	public static int addNewUser(User user) throws SQLException
	{
		DBHelper.getSQL("insert into t_user values(t_user_id_seq.nextval,?,?,?,?,?,?,0)");
		DBHelper.getPstm().setString(1,user.getUsername());
		DBHelper.getPstm().setString(2,user.getPassword());
		DBHelper.getPstm().setInt(3,user.getSex());
		DBHelper.getPstm().setString(4,user.getId_number());
		DBHelper.getPstm().setString(5,user.getTel());
		DBHelper.getPstm().setString(6,user.getAddr());
		return DBHelper.getPstm().executeUpdate();
	}
	
	/**
	 * 往数据库汽车信息表中添加新汽车。
	 * @param car 包含新汽车的信息的对象
	 * @return 添加成功返回汽车信息表中新汽车所在的行序数，添加失败返回0
	 * @throws SQLException
	 */
	public static int addNewCar(Car car) throws SQLException
	{
		DBHelper.getSQL("insert into t_car values(t_car_id_seq.nextval,?,?,?,?,?,?,?,?,?,?)");
		DBHelper.getPstm().setString(1,car.getCar_number());
		DBHelper.getPstm().setInt(2,car.getBrand_id());
		DBHelper.getPstm().setString(3,car.getModel());
		DBHelper.getPstm().setString(4,car.getColor());
		DBHelper.getPstm().setInt(5,car.getCategory_id());
		DBHelper.getPstm().setString(6,car.getT_comments());
		DBHelper.getPstm().setDouble(7,car.getPrice());
		DBHelper.getPstm().setDouble(8,car.getRent());
		DBHelper.getPstm().setInt(9,car.getStatus());
		DBHelper.getPstm().setInt(10,car.getUseable());
		return DBHelper.getPstm().executeUpdate();
	}
}
