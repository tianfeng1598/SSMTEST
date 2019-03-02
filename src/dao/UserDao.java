package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.User;

public interface UserDao {

	// 通过username 模糊查询
	List<User> findByName(String name);

	// 通过 code 查找用户
	User findByCode(String userCode);

	// 查找用户列表
	List<User> userlist(@Param("queryname") String queryname, @Param("queryUserRole") Integer queryUserRole);

	// 新增用户
	int addUser(User user);

	// 根据用户的id进行查找
	User findByUid(Integer uid);

	// 跟格用户的id进行删除操作
	int delUser(Integer uid);

	// 更新指定用户信息
	int updateUser(User user);

	// 修改指定用户密码
	int updatePwd(@Param("id")Integer id, @Param("newpassword")String newpassword);

}
