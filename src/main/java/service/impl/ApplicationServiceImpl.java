package service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import annotation.CustomerTransactional;
import dao.ApplicationDao;
import dao.ResourceDao;
import dao.RoleApplicationDao;
import dao.RoleResourceDao;
import model.Application;
import service.ApplicationService;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

	@Resource
	private ApplicationDao applicationDao;
	@Resource
	private RoleApplicationDao roleApplicationDao;
	@Resource
	private ResourceDao resourceDao;
	@Resource
	private RoleResourceDao roleResourceDao;

	public List<Application> GetApplicationList(String name, String typeUid,
			int showInMenu, int forbidden) {
		// TODO Auto-generated method stub
		return this.applicationDao.GetApplicationList(name, typeUid,
				showInMenu, forbidden);
	}

	public Application GetApplication(String uid) {
		// TODO Auto-generated method stub
		return this.applicationDao.GetApplication(uid);
	}

	@CustomerTransactional
	public void CreateApplication(Application application) {
		// TODO Auto-generated method stub
		this.applicationDao.CreateApplication(application);
	}

	@CustomerTransactional
	public void UpdateApplication(Application application) {
		// TODO Auto-generated method stub
		this.applicationDao.UpdateApplication(application);
	}

	@CustomerTransactional
	public void DeleteApplication(String uid) {
		// TODO Auto-generated method stub
		List<model.Resource> resources = this.resourceDao.GetResourceList(uid);
		if (resources != null && resources.size() > 0) {
			for (model.Resource item : resources) {
				this.roleResourceDao
						.DeleteRoleResourceByResource(item.getUid());
				this.resourceDao.DeleteResourceInfo(uid);
			}
		}

		this.applicationDao.DeleteApplication(uid);
		this.roleApplicationDao.DeleteRoleApplicationByApp(uid);
	}

	public List<Application> GetApplicationListByNotForbidden() {
		// TODO Auto-generated method stub
		return this.applicationDao.GetApplicationListByNotForbidden();
	}

}
