package service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import dao.RoleDao;
import entity.Role;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleDao roledao;

	// 查找角色列表
	@Override
	public List<Role> rolelist() {
		return roledao.rolelist();
	}

}
