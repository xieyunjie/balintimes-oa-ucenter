package dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import mappers.UserRolesMapper;
import model.UserRoles;
import dao.UserRolesDao;

@Repository
public class UserRolesDaoImpl implements UserRolesDao {

	@Resource
	private UserRolesMapper userRolesMapper;

	public List<UserRoles> GetUserRolesListByUser(String userUid) {
		// TODO Auto-generated method stub
		return this.userRolesMapper.GetUserRolesListByUser(userUid);
	}

	public void CreateUserRoles(UserRoles userRoles) {
		// TODO Auto-generated method stub
		this.userRolesMapper.CreateUserRoles(userRoles);
	}

	public void DeleteUserRoleByUserAndRole(String userUid, String roleUid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userUid", userUid);
		map.put("roleUid", roleUid);

		this.userRolesMapper.DeleteUserRoleByUserAndRole(map);
	}

	public void DeleteUserRoleByUser(String userUid) {
		// TODO Auto-generated method stub
		this.userRolesMapper.DeleteUserRoleByUser(userUid);
	}

	public void DeleteUserRoleByRole(String roleUid) {
		// TODO Auto-generated method stub
		this.userRolesMapper.DeleteUserRoleByRole(roleUid);
	}

}
