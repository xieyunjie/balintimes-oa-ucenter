package dao;

import java.util.List;

import model.UserRoles;

public interface UserRolesDao {
	List<UserRoles> GetUserRolesListByUser(String userUid);
	
	void CreateUserRoles(UserRoles userRoles);
	
	void DeleteUserRoleByUserAndRole(String userUid,String roleUid);
	
	void DeleteUserRoleByUser(String userUid);
	
	void DeleteUserRoleByRole(String roleUid);
}
