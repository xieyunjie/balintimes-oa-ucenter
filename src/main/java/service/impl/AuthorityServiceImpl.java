package service.impl;

import model.*;

import model.authority.Employee;
import org.springframework.stereotype.Service;

import dao.ApplicationDao;
import dao.ResourceDao;
import dao.RoleDao;
import dao.UserDao;
import service.AuthorityService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexXie on 2015/7/31.
 */

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@javax.annotation.Resource
	private ApplicationDao applicationDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private RoleDao roleDao;
	@javax.annotation.Resource
	private UserDao userDao;

	public List<Application> GetUserApplications(String username) {
		User user = this.userDao.getUserByName(username);
		if (!user.isIsadmin()) {
			return this.applicationDao.GetUserApplications(username);
		} else {
			return this.applicationDao.GetApplicationListByNotForbidden();
		}
	}

	private void SetResource(List<Resource> list, Resource item) {
		if (!item.getParentUid().equals("00000000-0000-0000-0000-000000000000")) {
			Resource r = this.resourceDao.GetResource(item.getParentUid());
			if (r.isForbidden() == false) {
				this.SetResource(list, r);
			}
		}
		if (!this.ExistsResourceByList(list, item)) {
			list.add(item);
		}
	}

	private boolean ExistsResourceByList(List<Resource> list, Resource item) {
		boolean b = false;
		for (Resource it : list) {
			if (it.getUid().equals(item.getUid())) {
				b = true;
			}
		}
		return b;
	}

	public List<Resource> GetUserMenu(String username, String applicationuid) {
		User user = this.userDao.getUserByName(username);
		List<Resource> list = new ArrayList<Resource>();
		if (!user.isIsadmin()) {
			List<Resource> rs = this.resourceDao.GetUserMenu(username,
					applicationuid);
			for (Resource item : rs) {
				this.SetResource(list, item);
			}
			return list;
		} else {
			List<Resource> rs = this.resourceDao
					.GetResourceListByNotForbidden(applicationuid);
			for (Resource item : rs) {
				if (item.getResourceType() == 1) {
					list.add(item);
				}
			}
			return list;
		}
	}

	public List<Resource> GetUserPermissions(String username,
			String applicationuid) {
		User user = this.userDao.getUserByName(username);
		if (!user.isIsadmin()) {
			return this.resourceDao
					.GetUserPermissions(username, applicationuid);
		} else {
			List<Resource> list = new ArrayList<Resource>();
			List<Resource> rs = this.resourceDao
					.GetResourceListByNotForbidden(applicationuid);
			for (Resource item : rs) {
				if (item.getResourceType() == 2) {
					list.add(item);
				}
			}
			return list;
		}
	}

	public List<Resource> GetUserMenuPermissions(String username, String menuuid) {
		return this.resourceDao.GetUserMenuPermissions(username, menuuid);
	}

	public List<Role> GetUserRoles(String username) {
		return this.roleDao.GetUserRoles(username);
	}

	public Employee GetEmployee(String username) {
		return null;
	}

	public List<Employee> GetSuperiors(String username) {
		return null;
	}

	public List<Employee> GetSubordinates(String username) {
		return null;
	}

}
