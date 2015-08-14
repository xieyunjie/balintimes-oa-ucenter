package dao;

import java.util.List;

import model.Resource;

public interface ResourceDao {
	List<Resource> GetResourceList(String appUid);

	List<Resource> GetResourceListByNotForbidden(String appUid);
	
	List<Resource> GetUserMenu(String userName,String appUid);
	
	List<Resource> GetUserPermissions(String userName,String appUid);
	
	List<Resource> GetUserMenuPermissions(String userName,String menuUid);
	
	int ExistsStateByResource(String state);
	
	Resource GetResource(String uid);

	void CreateResourceInfo(Resource resource);

	void UpdateResourceInfo(Resource resource);

	void DeleteResourceInfo(String uid);

	void DeleteResourceByParentUid(String parentUid);
}
