package com.oaec.view;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.dao.DBOperator;
import com.oaec.biz.Getter;
import com.oaec.biz.RecordDisplayer;

public class ReturnView
{
	/**
	 * 用户还车操作界面（根据指定的用户名和汽车编号，完善数据库租赁记录表中相应的租赁记录）。
	 * @param username 指定的用户名
	 * @param carId 指定的汽车编号
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
				System.out.println("还车成功\n记录如下：");
				RecordDisplayer.showReturnRecord(username,recordId);
				DBOperator.commit();
			}
			else
			{
				System.out.println("还车失败\n");
				DBOperator.rollBack();
			}
		}
		else if(!"".equals(carName))
			System.out.println("您当前没有租用"+carName+"\n");
		else
			System.out.println("查无此车\n");
	}
}
