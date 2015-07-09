package service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import annotation.CustomerTransactional;
import dao.RoleApplicationDao;
import dao.RoleResourceDao;
import model.RoleApplication;
import model.RoleResource;
import service.RoleResourceService;

@Service
public class RoleResourceServiceImpl implements RoleResourceService {

	@Resource
	private RoleResourceDao roleResourceDao;

	@Resource
	private RoleApplicationDao roleApplicationDao;

	public List<RoleResource> GetRoleResourceListByRole(String roleUid) {
		// TODO Auto-generated method stub
		return this.roleResourceDao.GetRoleResourceListByRole(roleUid);
	}

	@CustomerTransactional
	public void SaveRoleResource(String roleUid, String appUid,
			String resourceUid, boolean checked) {
		// TODO Auto-generated method stub
		int rrCount = this.roleApplicationDao.GetRoleApplicationCountByRole(
				roleUid, appUid);
		if (checked == true) {
			if (rrCount == 0) {
				RoleApplication ra = new RoleApplication();
				ra.setUid(UUID.randomUUID().toString());
				ra.setRoleUid(roleUid);
				ra.setAppUid(appUid);

				this.roleApplicationDao.CreateRoleApplication(ra);
			}

			RoleResource rr = new RoleResource();
			rr.setUid(UUID.randomUUID().toString());
			rr.setRoleUid(roleUid);
			rr.setResourcesUid(resourceUid);

			this.roleResourceDao.CreateRoleResource(rr);
		} else {
			this.roleResourceDao.DeleteRoleResourceByRoleAndResource(roleUid,
					resourceUid);

			int raCount = this.roleResourceDao
					.GetRoleResourceCountByRoleAndApp(roleUid, appUid);
			if (raCount == 0) {
				this.roleApplicationDao.DeleteRoleApplicationByRoleAndApp(
						roleUid, appUid);
			}
		}
	}

	@CustomerTransactional
	public void CleanSetting(String roleUid) {
		// TODO Auto-generated method stub
		this.roleApplicationDao.DeleteRoleApplicationByRole(roleUid);
		this.roleResourceDao.DeleteRoleResourceByRole(roleUid);
	}

}
