package dao;

import org.apache.ibatis.annotations.Param;

import entity.Bill;

public interface BillDao {

	// 根据供应商查找订单
	Bill findByProId(@Param("pid")Integer pid);

	

}
