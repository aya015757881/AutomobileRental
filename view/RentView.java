package com.oaec.view;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.dao.DBOperator;
import com.oaec.biz.Getter;
import com.oaec.biz.RecordDisplayer;

public class RentView
{
	/**
	 * �û��⳵�������棨����ָ�����û�����������������ݿ����޼�¼���в����µ����޼�¼����
	 * @param username ָ�����û���
	 * @param carId ָ�����������
	 * @throws SQLException
	 */
	public static void showView(String username,int carId) throws SQLException
	{
		ResultSet carRs = DBOperator.getResult("select * from t_car where id = ?",carId);
		if(carRs.next())
		{
			if(carRs.getInt("useable")==1)
				System.out.println(carRs.getString("model")+"�Ѿ��¼�\n");
			else if(carRs.getInt("status")==1)
				System.out.println(carRs.getString("model")+"�Ѿ����\n");
			else
			{
				int userId = Getter.getUserId(username);
				DBOperator.savePoint();
				int carUpdate = DBOperator.update("update t_car set status = 1 where id = ?",carId);
				int recordUpdate = DBOperator.update("insert into t_record(id,user_id,car_id,start_date,payment) "
						+ "values(t_record_id_seq.nextval,?,?,sysdate,0)",userId,carId);
				if(carUpdate>0&&recordUpdate>0)
				{
					System.out.println("�⳵�ɹ�\n��¼���£�");
					RecordDisplayer.showRentRecord(username,carId);
					DBOperator.commit();
				}
				else
				{
					System.out.println("�⳵ʧ��\n");
					DBOperator.rollBack();
				}
			}
		}
		else
			System.out.println("���޴˳�\n");
	}
}
