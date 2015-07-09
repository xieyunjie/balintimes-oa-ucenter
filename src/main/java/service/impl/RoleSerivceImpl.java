package service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import annotation.CustomerTransactional;
import dao.RoleApplicationDao;
import dao.RoleDao;
import dao.RoleResourceDao;
import dao.UserRolesDao;
import model.Role;
import service.RoleService;

@Service
public class RoleSerivceImpl implements RoleService {

	@Resource
	private RoleDao roleDao;
	@Resource
	private RoleResourceDao roleResourceDao;
	@Resource
	private RoleApplicationDao roleApplicationDao;
	@Resource
	private UserRolesDao userRolesDao;

	public List<Role> GetRoleList() {
		// TODO Auto-generated method stub
		return this.roleDao.GetRoleList();
	}

	public Role GetRole(String uid) {
		// TODO Auto-generated method stub
		return this.roleDao.GetRole(uid);
	}

	@CustomerTransactional
	public void CreateRole(Role role) {
		// TODO Auto-generated method stub
		this.roleDao.CreateRole(role);
	}

	@CustomerTransactional
	public void UpdateRole(Role role) {
		// TODO Auto-generated method stub
		this.roleDao.UpdateRole(role);
	}

	@CustomerTransactional
	public void DeleteRole(String uid) {
		// TODO Auto-generated method stub
		List<Role> list = this.GetRoleList();
		List<String> uids = new ArrayList<String>();

		uids.add(uid);
		this.SetRoles(list, uids, uid);

		for (String item : uids) {
			this.roleApplicationDao.DeleteRoleApplicationByRole(item);
			this.roleResourceDao.DeleteRoleResourceByRole(item);
			this.userRolesDao.DeleteUserRoleByRole(item);
			this.roleDao.DeleteRole(item);
		}

	}

	private void SetRoles(List<Role> list, List<String> uids, String parentuid) {
		for (Role item : list) {
			if (item.getParentUid().equals(parentuid)) {
				uids.add(item.getParentUid());
				this.SetRoles(list, uids, item.getUid());
			}
		}
	}

	public List<Role> GetRoleListByNotForbidden() {
		// TODO Auto-generated method stub
		return this.roleDao.GetRoleListByNotForbidden();
	}

}
