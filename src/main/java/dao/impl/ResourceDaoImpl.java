package dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import mappers.ResourceMapper;
import model.Resource;
import dao.ResourceDao;

@Repository
public class ResourceDaoImpl implements ResourceDao {

	@javax.annotation.Resource
	private ResourceMapper resourceMapper;

	public List<Resource> GetResourceList(String appUid) {
		// TODO Auto-generated method stub
		return this.resourceMapper.GetResourceList(appUid);
	}

	public Resource GetResource(String uid) {
		// TODO Auto-generated method stub
		return this.resourceMapper.GetResource(uid);
	}

	public void CreateResourceInfo(Resource resource) {
		// TODO Auto-generated method stub
		this.resourceMapper.CreateResourceInfo(resource);
	}

	public void UpdateResourceInfo(Resource resource) {
		// TODO Auto-generated method stub
		this.resourceMapper.UpdateResourceInfo(resource);
	}

	public void DeleteResourceInfo(String uid) {
		// TODO Auto-generated method stub
		this.resourceMapper.DeleteResourceInfo(uid);
	}

	public void DeleteResourceByParentUid(String parentUid) {
		// TODO Auto-generated method stub
		this.resourceMapper.DeleteResourceByParentUid(parentUid);
	}

	public List<Resource> GetResourceListByNotForbidden(String appUid) {
		// TODO Auto-generated method stub
		return this.resourceMapper.GetResourceListByNotForbidden(appUid);
	}

	public List<Resource> GetUserMenu(String userName, String appUid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		if (appUid.equals(""))
			appUid = null;
		map.put("appUid", appUid);
		return this.resourceMapper.GetUserMenu(map);
	}

	public List<Resource> GetUserPermissions(String userName, String appUid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		if (appUid.equals(""))
			appUid = null;
		map.put("appUid", appUid);
		return this.resourceMapper.GetUserPermissions(map);
	}

	public List<Resource> GetUserMenuPermissions(String userName, String menuUid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("menuUid", menuUid);
		return this.resourceMapper.GetUserMenuPermissions(map);
	}

}
