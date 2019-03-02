package controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entity.User;
import service.UserService;

@Controller
public class IndexController {

	@Resource(name = "userService")
	private UserService userservice;

	// 登录界面
	@RequestMapping("/login.html")
	public String getCount(Model md) {
		return "login";
	}

	// 登录请求
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam String userCode, @RequestParam String userPassword, HttpSession session,
			Model md) {
		User user = userservice.dologin(userCode, userPassword);
		if (user != null) {
			session.setAttribute("user", user);
			return "redirect:/main.html";
		} else {
			String msg = "用户或密码错误!请重新输入.......";
			session.setAttribute("msg", msg);
			return "redirect:/login.html";
		}
	}

	// 首页
	@RequestMapping("/main.html")
	public String getmain() {
		return "frame";
	}

	// 用户管理界面
	@RequestMapping("/user.html")
	public String getuser() {
		return "userlist";
	}

	// 用户增加页面
	@RequestMapping("/useradd.html")
	public String useradd() {
		return "useradd";
	}
	
	// 重定向至供应商界面
	@RequestMapping("/provide.html")
	public String aaa() {
		return "providerlist";
	}
}
