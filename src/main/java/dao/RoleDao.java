package dao;

import java.util.List;

import model.Role;

public interface RoleDao {
	List<Role> GetRoleList();
	
	List<Role> GetRoleListByNotForbidden();
	
	List<Role> GetUserRoles(String userName);
	
	Role GetRole(String uid);
	
	void CreateRole(Role role);

	void UpdateRole(Role role);

	void DeleteRole(String uid);

	void DeleteRoleByParentUid(String parentUid);
}
