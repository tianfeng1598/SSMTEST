package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Provider;

public interface ProviderDao {

	// 查找供应商列表
	List<Provider> providerlist(@Param("queryProCode")String queryProCode, @Param("queryProName")String queryProName);

	// 查看指定供应商信息
	Provider findByUid(@Param("uid")Integer uid);

	// 删除指定供应商
	int delpro(@Param("proid")Integer proid);

	// 修改信息
	int updatePro(Provider pro);

	// 添加供应商
	int addpro(Provider pro);

}
