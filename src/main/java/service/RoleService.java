package service;

import java.util.List;

import model.Role;

public interface RoleService {
	List<Role> GetRoleList();
	
	List<Role> GetRoleListByNotForbidden();
	
	Role GetRole(String uid);
	
	void CreateRole(Role role);

	void UpdateRole(Role role);

	void DeleteRole(String uid);
}
