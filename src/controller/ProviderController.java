package controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.Bill;
import entity.Provider;
import entity.User;
import service.BillService;
import service.ProviderService;
import tools.PageBean;

@Controller
@RequestMapping("/provider")
public class ProviderController {

	// 注入providerservice
	@Resource(name = "ProviderService")
	ProviderService providerservice;

	@Resource(name = "billService")
	BillService billservice;

	// 显示供应商页面(兼顾查询页面)
	@RequestMapping("provider")
	public String show(@RequestParam(required = false, value = "queryProCode") String queryProCode,
			@RequestParam(required = false, value = "num") Integer num,
			@RequestParam(required = false, value = "queryProName") String queryProName, HttpSession session) {
		// get请求下的转码
		try {
			if (queryProCode != null && queryProCode != "")
				queryProCode = new String(queryProCode.getBytes("ISO-8859-1"), "utf-8");
			if (queryProName != null && queryProName != "")
				queryProName = new String(queryProName.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 查找供应商列表
		List<Provider> providerList = providerservice.providerlist(queryProCode, queryProName);

		// 传递一个分页列表
		PageBean<Provider> pagebean = providerservice.proPageList(num, 8, providerList);
		session.setAttribute("pagebean", pagebean);

		// 返回一个搜索列表信息
		session.setAttribute("queryProCode", queryProCode);
		session.setAttribute("queryProName", queryProName);

		return "redirect:/provide.html";
	}

	// 通过条件进行查询 (用于页面跳转)
	@RequestMapping("/selectprovider.html")
	public String provider(String code, String name, Integer num, HttpSession session) {
		// get请求下的转码
		try {
			if (code != null && code != "")
				code = new String(code.getBytes("ISO-8859-1"), "utf-8");
			if (name != null && name != "")
				name = new String(name.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 查找供应商列表
		List<Provider> providerList = providerservice.providerlist(code, name);

		// 传递一个分页列表
		PageBean<Provider> pagebean = providerservice.proPageList(num, 8, providerList);
		session.setAttribute("pagebean", pagebean);

		// 返回一个搜索列表信息
		session.setAttribute("queryProCode", code);
		session.setAttribute("queryProName", name);

		return "redirect:/provide.html";
	}

	// // AJAX查询单个 ,produces={"application/json;charset=UTF-8"} restful 风格
	// @RequestMapping(value = "/view/{proid}")
	// @ResponseBody
	// public Provider findById(@PathVariable("proid") Integer proid) {
	// Provider pro = providerservice.findByUid(proid);
	// return pro;
	// }

	// restful 风格  
	@RequestMapping(value = "/view/{proid}")
	public String findById(@PathVariable("proid") Integer proid,Model mod) {
		Provider pro = providerservice.findByUid(proid);
		mod.addAttribute("provider", pro);
		return "providerview";
	}

	// ajax进行单个删除
	@RequestMapping(value = "/del/{proid}")
	@ResponseBody
	public Object deleted(@PathVariable("proid") Integer proid) {
		Map<String, Object> map = new HashMap<>();
		// 查找该供应商
		Provider pro = providerservice.findByUid(proid);
		if (pro == null) { // 供应商不存在
			map.put("delResult", "notexist");
		} else {
			// 判断供应商下是否有订单
			Bill bill = billservice.findByPro(pro);
			if (bill == null) {
				int ret = providerservice.delpro(proid);
				if (ret > 0) {
					map.put("delResult", "true");
				} else {
					map.put("delResult", "false");
				}
			} else {
				map.put("delResult", "havebill");
			}
		}
		return map;
	}

	// 显示修改页面
	@RequestMapping(value = "/modify/{proid}")
	public String showProModify(@PathVariable("proid") Integer proid, Model model) {
		Provider oldpro = providerservice.findByUid(proid);
		// 将查找的对象直入model中进行传递
		model.addAttribute("provider", oldpro);
		return "providermodify";
	}

	// 对特定供应商进行修改
	@RequestMapping(value = "/domodify")
	public String doModify(Provider pro, HttpSession session) {
		User userlogin = (User) session.getAttribute("user");
		if(userlogin != null){
			pro.setModifyBy(userlogin.getId());
			pro.setModifyDate(new Date());
			int ret = providerservice.updatePro(pro);
			if (ret > 0) {
				return "redirect:/provide.html";
			} else {
				return "providermodify";
			}
		}else{
			return "login";
		}
		
	}

	/*添加供应商信息 provideradd*/
	// 显示添加页面
	@RequestMapping(value = "/provideradd")
	public String showAdd(){
		return "provideradd";
	}
	
	// 添加供应商信息  doAdd
	@RequestMapping(value = "/doAdd")
	public String doAdd(Provider pro){
		pro.setCreationDate(new Date());
		int ret = providerservice.addpro(pro);
		if(ret > 0)
			return "redirect:/provide.html";
		else
			return "provideradd";
	}
	
}
