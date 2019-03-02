package service;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import dao.UserDao;
import entity.User;
import tools.PageBean;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	// 通过用户名进行模糊查询
	@Override
	public List<User> findByName(String name) {
		return userDao.findByName(name);
	}

	// 登录验证
	@Override
	public User dologin(String userCode, String userPassword) {
		User user = userDao.findByCode(userCode);
		if (user != null && user.getUserPassword().equals(userPassword)) {
			return user;
		}
		return null;
	}

	// 查找用户列表
	@Override
	public List<User> userlist(String queryname,Integer queryUserRole) {
		return userDao.userlist(queryname,queryUserRole);
	}

	// 查找用户的分页对象
	@Override
	public PageBean<User> userPageList(Integer pageNo, Integer pageSize, List<User> userlist) {
		PageBean<User> userPage = new PageBean<>();
		List<User> userList = new LinkedList<User>();
		// 设置默认值
		int page = 8;
		int pageno = 1;
		// 传入总信息条数
		userPage.setInfonum(userlist.size());
		// 传入每页容量
		if(pageSize != null)
			page = pageSize;
		userPage.setPage(page);
		// 查询总页数
		int pagetotal = userPage.getPagetotal();
		// 传入当前页码
		if(pageNo != null)
			pageno = pageNo;
		userPage.setPageno(pageno);
		// 接收每页信息列表
		if(userlist.size() > page){
			int from = (pageno-1)*page;
			if((from+page)>userlist.size()){
				userList = userlist.subList(from, userlist.size());
			}else{
				userList = userlist.subList(from, (from+page));
			}
		}else{
			userList = userlist;
		}
		userPage.setPagelist(userList);
		return userPage;
	}

	// 验证用户的usercode是否可用
	@Override
	public boolean checkUserCode(String userCode) {
		User user = userDao.findByCode(userCode);
		if(user != null)
			return true;
		else
			return false;
	}

	// 新增用户
	@Override
	public int addUser(User user) {
		return userDao.addUser(user);
	}

	// 根据用户的id属性进行查找
	@Override
	public User findByUid(Integer uid) {
		return userDao.findByUid(uid);
	}

	// 根据用户id进行删除操作
	@Override
	public int delUser(Integer uid) {
		return userDao.delUser(uid);
	}

	// 更新指定用户信息
	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	// 修改指定用户密码
	@Override
	public int updatePwd(Integer id, String newpassword) {
		return userDao.updatePwd(id,newpassword);
	}

}
