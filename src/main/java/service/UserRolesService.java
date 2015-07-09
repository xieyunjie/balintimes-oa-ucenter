package service;

import java.util.List;

import model.UserRoles;

public interface UserRolesService {
	List<UserRoles> GetUserRolesListByUser(String userUid);
	
	void SaveUserRoles(String userUid,String roleUid,boolean checked);
	
	void CleanUserRoles(String userUid);
}
