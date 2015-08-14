package service;

import java.util.List;

import model.Resource;

public interface ResourceService {
	List<Resource> GetResourceList(String appUid);
	
	List<Resource> GetResourceListByNotForbidden(String appUid);
	
	Resource GetResource(String uid);
	
	void CreateResourceInfo(Resource resource)throws Exception;

	void UpdateResourceInfo(Resource resource)throws Exception;

	void DeleteResourceInfo(String uid);
}
