package mappers;

import model.UserRoles;

import java.util.List;
import java.util.Map;

public interface UserRolesMapper {
	List<UserRoles> GetUserRolesListByUser(String userUid);
	
	void CreateUserRoles(UserRoles userRoles);
	
	void DeleteUserRoleByUserAndRole(Map<String, Object> map);
	
	void DeleteUserRoleByUser(String userUid);
	
	void DeleteUserRoleByRole(String roleUid);
}
