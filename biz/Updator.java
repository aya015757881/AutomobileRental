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
	 * ����Ա�޸�ָ����ŵ�������Ϣ�Ĳ������档
	 * @param carId �����ı��
	 * @throws SQLException
	 */
	public static void updateCarInfo(int carId) throws SQLException
	{
		if(!Checker.carExists(carId))
			System.out.println("���޴˳���\n");
		else if(Checker.carRented(carId))
			System.out.println(Getter.getCarName(carId)+"�ѱ�������δ�黹��ֻ�й黹������޸�����Ϣ\n");
		else
		{
			CarDisplayer.showByCarId(carId);
			System.out.println("1.�޸����  2.�޸����¼�״̬");
			switch(Keyboard.next())
			{
			case "1":
				System.out.println("�������µ����");
				String rent;
				do rent = Keyboard.next();
				while(!Checker.isLegalDouble(rent));
				if(updateRent(carId,Getter.parseDouble(rent))>0)
					System.out.println("�޸ĳɹ�!");
				else
					System.out.println("�޸�ʧ��!");
				CarDisplayer.showByCarId(carId);
				break;
			case "2":
				if(updateAvailability(carId)>0)
					System.out.println("�޸ĳɹ���");
				else
					System.out.println("�޸�ʧ�ܣ�");
				CarDisplayer.showByCarId(carId);
				break;
			default:
				System.out.println("��Чָ��");
			}
		}
	}
	
	/**
	 * �޸�ָ����ŵ����������
	 * @param carId ָ�����������
	 * @param rent Ŀ�����
	 * @return �޸ĳɹ�����������Ϣ�����������򷵻�0
	 * @throws SQLException
	 */
	private static int updateRent(int carId,double rent) throws SQLException
	{
		return DBOperator.update("update t_car set rent = ? where id = ?",rent,carId);
	}
	
	/**
	 * �޸�ָ����ŵ����������¼�״̬��
	 * @param carId ָ�����������
	 * @return ���޸ĳɹ����������ݿ�������Ϣ���и��������ڵ������������򷵻�0
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
	 * ����Ա���һ���������Ĳ������档
	 * @throws SQLException
	 */
	public static void addCar() throws SQLException
	{
		Car car = new Car();
		System.out.println("��Ϊ�³����������Ϣ��");
		System.out.println("Ʒ�ƣ�1.���� 2.���� 3.�µ� 4.���� 5����");
		String brandId;
		do brandId = Keyboard.next();
		while(!Checker.isLegalStringOfOptionIndex(brandId,"brand"));
		car.setBrand_id(Getter.optionIndex(brandId));
		System.out.println("���ͣ�1.������ 2.������ 3.SUV 4.��Ӣ��");
		String categoryId;
		do categoryId = Keyboard.next();
		while(!Checker.isLegalStringOfOptionIndex(categoryId,"category"));
		car.setCategory_id(Getter.optionIndex(categoryId));
		System.out.println("���ƣ�");
		car.setModel(Keyboard.next());
		System.out.println("���ƺţ�");
		String carNum;
		do carNum = Keyboard.next();
		while(Checker.carNumberExists(carNum));
		car.setCar_number(carNum);
		System.out.println("��Ҫ��");
		car.setT_comments(Keyboard.next());
		System.out.println("��ɫ��");
		car.setColor(Keyboard.next());
		System.out.println("�ۼۣ�");
		String price;
		do price = Keyboard.next();
		while(!Checker.isLegalDouble(price));
		car.setPrice(Getter.parseDouble(price));
		System.out.println("ÿ�����");
		String rent;
		do rent = Keyboard.next();
		while(!Checker.isLegalDouble(rent));
		car.setRent(Getter.parseDouble(rent));
		System.out.println("�Ƿ���⣺ ��0.����  1.�����⣩");
		String status;
		do status = Keyboard.next();
		while(!Checker.isLegalStringOfOptionIndex(status,"status"));
		car.setStatus(Getter.optionIndex(status));
		System.out.println("�Ƿ��ϼܣ���0.�ϼ�  1.�¼ܣ�");
		String useable;
		do useable = Keyboard.next();
		while(!Checker.isLegalStringOfOptionIndex(useable,"useable"));
		car.setUseable(Getter.optionIndex(useable));
		
		if(addNewCar(car)>0)
		{
			System.out.println("��ӳɹ�!");
			CarDisplayer.showByCarNumber(car.getCar_number());
		}
		else
			System.out.println("���ʧ��!");
	}
	
	/**
	 * ����Աɾ��ָ����ŵ������Ĳ������档
	 * @param carId �����ı��
	 * @throws SQLException
	 */
	public static void removeCar(int carId) throws SQLException
	{
		if(!Checker.carExists(carId))
			System.out.println("���޴˳�!\n");
		else if(Checker.carRented(carId))
			System.out.println(Getter.getCarName(carId)+"�ѱ�������δ�黹��ֻ�й黹����ܰ���ɾ��\n");
		else
		{
			System.out.println("ȷ��ɾ��"+Getter.getCarName(carId)+"��\n1.ȷ��  2.ȡ��\n");
			CarDisplayer.showByCarId(carId);
			String command;
			if(Checker.isLegalCommand1(command = Keyboard.next()))
			{
				if("1".equals(command))
				{
					if(deleteCar(carId)>0)
						System.out.println("ɾ���ɹ���\n");
					else
						System.out.println("ɾ��ʧ�ܣ�\n");
				}
				else
					System.out.println("ɾ��������ȡ��!\n");
			}
			else
				System.out.println("��Чָ�\n");
		}
	}
	
	/**
	 * ɾ��ָ����ŵ�������
	 * @param carId ָ�����������
	 * @return ��ɾ���ɹ����������ݿ�������Ϣ���и�����ԭ�����ڵ������������򷵻�0
	 * @throws SQLException
	 */
	private static int deleteCar(int carId) throws SQLException
	{
		int dRec = DBOperator.update("delete from t_record where car_id = ?",carId);
		int dCar = DBOperator.update("delete from t_car where id = ?",carId);
		return dRec>0||dCar>0?1:0;
	}
	
	/**
	 * �����ݿ��û�����������û���
	 * @param user �������û�ע����Ϣ�Ķ���
	 * @return ��ӳɹ������û��������û����ڵ������������ʧ�ܷ���0
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
	 * �����ݿ�������Ϣ���������������
	 * @param car ��������������Ϣ�Ķ���
	 * @return ��ӳɹ�����������Ϣ�������������ڵ������������ʧ�ܷ���0
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
