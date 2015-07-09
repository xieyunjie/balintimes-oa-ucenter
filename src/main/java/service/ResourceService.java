package service;

import java.util.List;

import model.Resource;

public interface ResourceService {
	List<Resource> GetResourceList(String appUid);

	List<Resource> GetResourceListByNotForbidden(String appUid);
	
	Resource GetResource(String uid);
	
	void CreateResourceInfo(Resource resource);

	void UpdateResourceInfo(Resource resource);

	void DeleteResourceInfo(String uid);
}
