package service;

import entity.Bill;
import entity.Provider;

public interface BillService {

	// 根据供应商查找订单
	Bill findByPro(Provider pro);

}
