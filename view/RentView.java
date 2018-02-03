package com.oaec.view;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.oaec.dao.DBOperator;
import com.oaec.biz.Getter;
import com.oaec.biz.RecordDisplayer;

public class RentView
{
	/**
	 * 用户租车操作界面（根据指定的用户名和汽车编号在数据库租赁记录表中产生新的租赁记录）。
	 * @param username 指定的用户名
	 * @param carId 指定的汽车编号
	 * @throws SQLException
	 */
	public static void showView(String username,int carId) throws SQLException
	{
		ResultSet carRs = DBOperator.getResult("select * from t_car where id = ?",carId);
		if(carRs.next())
		{
			if(carRs.getInt("useable")==1)
				System.out.println(carRs.getString("model")+"已经下架\n");
			else if(carRs.getInt("status")==1)
				System.out.println(carRs.getString("model")+"已经租出\n");
			else
			{
				int userId = Getter.getUserId(username);
				DBOperator.savePoint();
				int carUpdate = DBOperator.update("update t_car set status = 1 where id = ?",carId);
				int recordUpdate = DBOperator.update("insert into t_record(id,user_id,car_id,start_date,payment) "
						+ "values(t_record_id_seq.nextval,?,?,sysdate,0)",userId,carId);
				if(carUpdate>0&&recordUpdate>0)
				{
					System.out.println("租车成功\n记录如下：");
					RecordDisplayer.showRentRecord(username,carId);
					DBOperator.commit();
				}
				else
				{
					System.out.println("租车失败\n");
					DBOperator.rollBack();
				}
			}
		}
		else
			System.out.println("查无此车\n");
	}
}
