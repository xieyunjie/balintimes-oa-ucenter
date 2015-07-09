package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import annotation.CustomerTransactional;
import dao.ResourceDao;
import dao.RoleResourceDao;
import model.Resource;
import service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private RoleResourceDao roleResourceDao;
	
	public List<Resource> GetResourceList(String appUid) {
		// TODO Auto-generated method stub
		return this.resourceDao.GetResourceList(appUid);
	}

	public Resource GetResource(String uid) {
		// TODO Auto-generated method stub
		return this.resourceDao.GetResource(uid);
	}

	@CustomerTransactional
	public void CreateResourceInfo(Resource resource) {
		// TODO Auto-generated method stub
		this.resourceDao.CreateResourceInfo(resource);
	}

	@CustomerTransactional
	public void UpdateResourceInfo(Resource resource) {
		// TODO Auto-generated method stub
		this.resourceDao.UpdateResourceInfo(resource);
	}

	@CustomerTransactional
	public void DeleteResourceInfo(String uid) {
		// TODO Auto-generated method stub
		Resource model=this.GetResource(uid);
		List<Resource> list=this.GetResourceList(model.getAppUid());
		List<String> uids=new ArrayList<String>();
		
		uids.add(uid);
		this.SetResource(list, uids, uid);
		
		for(String item:uids){
			this.roleResourceDao.DeleteRoleResourceByResource(item);
			this.resourceDao.DeleteResourceInfo(item);
		}
	}
	
	private void SetResource(List<Resource> list, List<String> uids, String parentuid) {
		for (Resource item : list) {
			if (item.getParentUid().equals(parentuid)) {
				uids.add(item.getParentUid());
				this.SetResource(list, uids, item.getUid());
			}
		}
	}

	public List<Resource> GetResourceListByNotForbidden(String appUid) {
		// TODO Auto-generated method stub
		return this.resourceDao.GetResourceListByNotForbidden(appUid);
	}

}
