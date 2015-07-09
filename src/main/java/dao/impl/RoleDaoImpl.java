package dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import mappers.RoleMapper;
import model.Role;
import dao.RoleDao;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Resource
	private RoleMapper roleMapper;

	public List<Role> GetRoleList() {
		// TODO Auto-generated method stub
		return this.roleMapper.GetRoleList();
	}

	public Role GetRole(String uid) {
		// TODO Auto-generated method stub
		return this.roleMapper.GetRole(uid);
	}

	public void CreateRole(Role role) {
		// TODO Auto-generated method stub
		this.roleMapper.CreateRole(role);
	}

	public void UpdateRole(Role role) {
		// TODO Auto-generated method stub
		this.roleMapper.UpdateRole(role);
	}

	public void DeleteRole(String uid) {
		// TODO Auto-generated method stub
		this.roleMapper.DeleteRole(uid);
	}

	public void DeleteRoleByParentUid(String parentUid) {
		// TODO Auto-generated method stub
		this.roleMapper.DeleteRoleByParentUid(parentUid);
	}

	public List<Role> GetRoleListByNotForbidden() {
		// TODO Auto-generated method stub
		return this.roleMapper.GetRoleListByNotForbidden();
	}

	public List<Role> GetUserRoles(String userName) {
		// TODO Auto-generated method stub
		return this.roleMapper.GetUserRoles(userName);
	}

}
