package dao;

import java.util.List;

import model.RoleApplication;

public interface RoleApplicationDao {
	List<RoleApplication> GetRoleApplicationListByRole(String roleUid);
	
	int GetRoleApplicationCountByRole(String roleUid,String appUid);
	
	void CreateRoleApplication(RoleApplication roleApplication);
	
	void DeleteRoleApplicationByRoleAndApp(String roleUid,String appUid);

	void DeleteRoleApplicationByRole(String roleUid);

	void DeleteRoleApplicationByApp(String appUid);
}
