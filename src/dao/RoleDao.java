package dao;

import java.util.List;

import entity.Role;

public interface RoleDao {

	// 查找角色列表
	List<Role> rolelist();
}
