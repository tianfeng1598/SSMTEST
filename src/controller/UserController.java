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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.Role;
import entity.User;
import service.RoleService;
import service.UserService;
import tools.PageBean;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource(name = "userService")
	private UserService userservice;

	@Resource(name = "roleService")
	private RoleService roleservice;

	// 退出请求
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "loginoutsuccess";
	}

	// 用户管理请求
	@RequestMapping("/user")
	public String user(@RequestParam(required = false, value = "pageNo") Integer pageNo,
			@RequestParam(required = false, value = "pageSize") Integer pageSize,
			@RequestParam(required = false, value = "queryname") String queryname,
			@RequestParam(required = false, value = "queryUserRole") Integer queryUserRole, HttpSession session) {
		try {
			if (queryname != null && queryname != "")
				queryname = new String(queryname.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 传递一个用户列表
		List<User> userList = userservice.userlist(queryname, queryUserRole);

		// 传递一个用户分页对象
		PageBean<User> pagebean = userservice.userPageList(pageNo, pageSize, userList);
		session.setAttribute("pagebean", pagebean);

		// 传递一个角色列表
		List<Role> roleList = roleservice.rolelist();
		session.setAttribute("roleList", roleList);

		// 返回一个搜索列表信息
		session.setAttribute("queryname", queryname);
		session.setAttribute("queryUserRole", queryUserRole);

		// 重定向至用户管理界面
		return "redirect:/user.html";
	}

	// 用于处理页面用户查找的请求  (页面跳转用)
	@RequestMapping("/find")
	public String findUser(String name, Integer role, Integer num, HttpSession session) {
		try {
			if (name != null && name != "")
				name = new String(name.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 传递一个用户列表
		List<User> userList = userservice.userlist(name, role);

		// 传递一个用户分页对象
		PageBean<User> pagebean = userservice.userPageList(num, 8, userList);
		session.setAttribute("pagebean", pagebean);

		// 传递一个角色列表
		List<Role> roleList = roleservice.rolelist();
		session.setAttribute("roleList", roleList);

		// 返回一个搜索列表信息
		session.setAttribute("queryname", name);
		session.setAttribute("queryUserRole", role);

		// 重定向至用户管理界面
		return "redirect:/user.html";
	}

	// 添加用户时的请求
	@RequestMapping("/useradd")
	public String useradd() {
		return "redirect:/useradd.html";
	}

	// ajax异步请求用户角色列表
	@RequestMapping(value = "/rolelist", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object rolelist() {
		List<Role> roleList = roleservice.rolelist();
		return roleList;
	}

	@RequestMapping("/checkUserCode")
	@ResponseBody
	public Object checkUserCode(
			String userCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 验证usercode是否存在
		if (userservice.checkUserCode(userCode)) {
			map.put("userCode", "exist");
		} else {
			map.put("userCode", "notExist");
		}
		return map;
	}

	// 添加用户时的请求
	@RequestMapping("/creatuser")
	public String creatuser(HttpSession session, User user) {
		User userLogin = (User) session.getAttribute("user");
		user.setCreatedBy(userLogin.getId());
		user.setCreationDate(new Date());
		int ret = userservice.addUser(user);
		if (ret > 0) {
			return "success";
		} else {
			return "useradd";
		}
	}

	// AJAX查询单个 ,produces={"application/json;charset=UTF-8"} restful 风格
	@RequestMapping(value = "/view/{uid}")
	@ResponseBody
	public User findById(@PathVariable("uid") Integer uid) {
		User user = userservice.findByUid(uid);
		return user;
	}

	// 显示修改页面
	@RequestMapping(value = "/modify/{userid}")
	public String showUserMOdify(@PathVariable("userid") Integer userid,Model model){
		User olduser = userservice.findByUid(userid);
		// 将查找的对象直入model中进行传递
		model.addAttribute("olduser", olduser);
		return "usermodify";
	}
	
	// 对特定用户进行修改
	@RequestMapping(value = "/domodify.html")
	public String doModify(User user,HttpSession session) {
		User userlogin = (User) session.getAttribute("user");
		user.setModifyBy(userlogin.getId());
		user.setModifyDate(new Date());
		int ret = userservice.updateUser(user);
		if(ret>0){
			return "success";
		}else{
			return "useradd";
		}
	}
	
	// ajax进行单个删除
	@RequestMapping(value = "/del/{userid}")
	@ResponseBody
	public Object deleted(@PathVariable("userid") Integer uid) {
		Map<String, Object> map = new HashMap<>();
		User user = userservice.findByUid(uid);
		if(user==null){
			map.put("delResult", "notexist");
		}else{
			int ret = userservice.delUser(uid);
			if(ret>0){
				map.put("delResult", "true");
			}else{
				map.put("delResult", "false");
			}
		}
		return map;
	}
	
	// 显示修改密码界面
	@RequestMapping(value = "/pwdmodify.html")
	public String pwdmodify() {
		return "pwdmodify";
	}
	
	// 修改用户密码  ---> 验证用户密码
	@RequestMapping(value = "/ispwd")
	@ResponseBody
	public Object ispwd(String oldpassword,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		User userlogin = (User) session.getAttribute("user");
		if(userlogin == null){  // 当前用户不存在
			map.put("result", "sessionerror");
		}else if(oldpassword == null || oldpassword == ""){  // 旧密码未输入
			map.put("result", "error");
		}else if(!userlogin.getUserPassword().equals(oldpassword)){  // 旧密码不正确
			map.put("result", "false");
		}else{  // 旧密码符合要求
			map.put("result", "true");
		}
		return map;
	}
	
	// 执行密码修改操作
	@RequestMapping(value = "/topwdmodify.html")
	public String topwdmodify(String newpassword,HttpSession session){
		User userlogin = (User) session.getAttribute("user");
		int ret = userservice.updatePwd(userlogin.getId(),newpassword);
		if(ret > 0){
			return "success";
		}else{
			session.setAttribute("message", "修改密码失败!");
			return "pwdmodify";
		}
	}
	
}
