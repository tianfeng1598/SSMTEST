package service;

import java.util.List;

import entity.Provider;
import entity.User;
import tools.PageBean;

public interface ProviderService {

	// 查询供应商列表
	List<Provider> providerlist(String queryProCode, String queryProName);

	// 查询分页
	PageBean<Provider> proPageList(Integer pageNo, Integer pageSize, List<Provider> providerList);

	// 查看指定供应商信息
	Provider findByUid(Integer proid);

	// 删除指定供应商
	int delpro(Integer proid);

	// 修改信息
	int updatePro(Provider pro);

	// 添加供应商信息
	int addpro(Provider pro);

}
