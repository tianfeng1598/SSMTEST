package service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.BillDao;
import entity.Bill;
import entity.Provider;

@Service("billService")
public class BillServiceImpl implements BillService {
	
	@Resource
	BillDao billdao;

	// 根据供应商查找订单
	@Override
	public Bill findByPro(Provider pro) {
		return billdao.findByProId(pro.getid());
	}

}
