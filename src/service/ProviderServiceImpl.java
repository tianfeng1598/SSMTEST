package service;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.ProviderDao;
import entity.Provider;
import tools.PageBean;

@Service("ProviderService")
public class ProviderServiceImpl implements ProviderService {
	
	// 注入dao层
	@Resource
	ProviderDao providerdao;

	// 查找供应商列表
	@Override
	public List<Provider> providerlist(String queryProCode, String queryProName) {
		return providerdao.providerlist(queryProCode, queryProName);
	}

	// 查询分页
	@Override
	public PageBean<Provider> proPageList(Integer pageNo, Integer pageSize, List<Provider> providerList) {
		PageBean<Provider> proPage = new PageBean<>();
		List<Provider> proList = new LinkedList<Provider>();
		// 设置默认值
		int page = 8;
		int pageno = 1;
		// 传入总信息条数
		proPage.setInfonum(providerList.size());
		// 传入每页容量
		if(pageSize != null)
			page = pageSize;
		proPage.setPage(page);
		// 查询总页数
		int pagetotal = proPage.getPagetotal();
		// 传入当前页码
		if(pageNo != null)
			pageno = pageNo;
		proPage.setPageno(pageno);
		// 接收每页信息列表
		//System.out.println(proPage.getPagelist().size());
		
		if(providerList.size() > page){
			int from = (pageno-1)*page;
			if((from+page)>providerList.size()){
				proList = providerList.subList(from, providerList.size());
			}else{
				proList = providerList.subList(from, (from+page));
			}
		}else{
			proList = providerList;
		}
		proPage.setPagelist(proList);
		return proPage;
	}

	// 查看指定供应商信息
	@Override
	public Provider findByUid(Integer proid) {
		return providerdao.findByUid(proid);
	}

	// 删除指定供应商
	@Override
	public int delpro(Integer proid) {
		return providerdao.delpro(proid);
	}

	// 修改信息
	@Override
	public int updatePro(Provider pro) {
		return providerdao.updatePro(pro);
	}

	// 添加供应商
	@Override
	public int addpro(Provider pro) {
		return providerdao.addpro(pro);
	}

}
