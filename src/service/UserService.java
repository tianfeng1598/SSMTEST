package service;

import java.util.List;

import entity.User;
import tools.PageBean;

public interface UserService {

	// 通过username 模糊查询
	List<User> findByName(String name);

	// 登录验证
	User dologin(String userCode, String userPassword);

	// 查找用户列表
	List<User> userlist(String queryname, Integer queryUserRole);

	// 查找一个用户的分页对象
	PageBean<User> userPageList(Integer pageNo, Integer pageSize, List<User> userlist);

	// 判断用户的usercode是否存在
	boolean checkUserCode(String userCode);

	// 新增用户
	int addUser(User user);

	// 根据用户的id查找
	User findByUid(Integer uid);

	// 对单个用户进行删除
	int delUser(Integer uid);

	// 更新指定用户信息
	int updateUser(User user);

	// 修改指定用户密码
	int updatePwd(Integer id, String newpassword);

}
