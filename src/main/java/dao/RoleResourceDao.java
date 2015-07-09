package dao;

import java.util.List;

import model.RoleResource;

public interface RoleResourceDao {
	List<RoleResource> GetRoleResourceListByRole(String roleUid);

	int GetRoleResourceCountByRoleAndApp(String roleUid, String appUid);

	void CreateRoleResource(RoleResource roleResource);

	void DeleteRoleResourceByRoleAndResource(String roleUid, String resourceUid);

	void DeleteRoleResourceByRole(String roleUid);
	
	void DeleteRoleResourceByResource(String resourceUid);
}
