package service;

import java.util.List;

import model.RoleResource;

public interface RoleResourceService {
	List<RoleResource> GetRoleResourceListByRole(String roleUid);
	
	void SaveRoleResource(String roleUid,String appUid,String resourceUid,boolean checked);
	
	void CleanSetting(String roleUid);
	
	void SaveRoleResources(String roleUid,String appUid,String[] resources);
}
