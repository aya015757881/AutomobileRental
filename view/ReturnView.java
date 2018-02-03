package com.oaec.view;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.dao.DBOperator;
import com.oaec.biz.Getter;
import com.oaec.biz.RecordDisplayer;

public class ReturnView
{
	/**
	 * �û������������棨����ָ�����û�����������ţ��������ݿ����޼�¼������Ӧ�����޼�¼����
	 * @param username ָ�����û���
	 * @param carId ָ�����������
	 * @throws SQLException
	 */
	public static void showView(String username,int carId) throws SQLException
	{
		int userId = Getter.getUserId(username);
		String carName = Getter.getCarName(carId);
		ResultSet recordidRs = DBOperator.getResult("select id from t_record where user_id = ? and car_id = ? "
				+ "and payment = 0",userId,carId);
		if(recordidRs.next())
		{
			int recordId = recordidRs.getInt("id");
			ResultSet rentRs = DBOperator.getResult("select rent from t_car where id = ?",carId);
			rentRs.next();
			double rent = rentRs.getDouble("rent");
			DBOperator.savePoint();
			int carReturn = DBOperator.update("update t_car set status = 0 where id = ?",carId);
			int dateComplete = DBOperator.update("update t_record set return_date = sysdate where "
					+ "id = ?",recordId);
			ResultSet intervalRs = DBOperator.getResult("select return_date - start_date from t_record where id = ?",recordId);
			intervalRs.next();
			int interval = intervalRs.getInt(1);
			if(interval == 0)
				interval++;
			int recordComplete = DBOperator.update("update t_record set payment = ? where id = ?",rent*interval,recordId);
			if(carReturn>0&&dateComplete>0&&recordComplete>0)
			{
				System.out.println("�����ɹ�\n��¼���£�");
				RecordDisplayer.showReturnRecord(username,recordId);
				DBOperator.commit();
			}
			else
			{
				System.out.println("����ʧ��\n");
				DBOperator.rollBack();
			}
		}
		else if(!"".equals(carName))
			System.out.println("����ǰû������"+carName+"\n");
		else
			System.out.println("���޴˳�\n");
	}
}
