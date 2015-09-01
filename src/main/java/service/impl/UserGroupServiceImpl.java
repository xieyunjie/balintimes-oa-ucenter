package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import annotation.CustomerTransactional;
import dao.RoleTypeDao;
import dao.UserDao;
import dao.UserGroupDao;
import dao.UserGroupDetailDao;
import model.GroupRoleType;
import model.RoleType;
import model.User;
import model.UserGroup;
import model.UserGroupDetail;
import model.UserGroupRoleTypeTree;
import model.UserGroupTree;
import service.UserGroupService;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Resource
	private UserGroupDao userGroupDao;
	@Resource
	private UserGroupDetailDao userGroupDetailDao;
	@Resource
	private UserDao userDao;
	@Resource
	private RoleTypeDao roleTypeDao;

	public List<UserGroup> GetUserGroupList(String name) {
		// TODO Auto-generated method stub
		return this.userGroupDao.GetUserGroupList(name);
	}

	public UserGroup GetUserGroup(String uid) {
		// TODO Auto-generated method stub
		return this.userGroupDao.GetUserGroup(uid);
	}

	@CustomerTransactional
	public void CreateUserGroup(UserGroup userGroup) {
		// TODO Auto-generated method stub
		userGroup.setUid(UUID.randomUUID().toString());
		this.userGroupDao.CreateUserGroup(userGroup);
	}

	@CustomerTransactional
	public void UpdateUserGroup(UserGroup userGroup) {
		// TODO Auto-generated method stub
		this.userGroupDao.UpdateUserGroup(userGroup);
	}

	@CustomerTransactional
	public void DeleteUserGroup(String uid) {
		// TODO Auto-generated method stub
		this.userGroupDetailDao.DeleteUserGroupDetailByGroup(uid);
		this.userGroupDao.DeleteUserGroup(uid);
	}

	@CustomerTransactional
	public void DeleteUserGroupDetailByGroup(String groupUid) {
		this.userGroupDetailDao.DeleteUserGroupDetailByGroup(groupUid);
	}

	@CustomerTransactional
	public void DeleteUserGroupDetail(String userGroupDetailUid) {
		this.userGroupDetailDao.DeleteUserGroupDetail(userGroupDetailUid);
	}

	public List<UserGroupTree> GetUserGroupByTree(String name) {
		// TODO Auto-generated method stub
		List<UserGroupTree> tree = new ArrayList<UserGroupTree>();
		List<UserGroup> list = this.GetUserGroupList(name);
		List<RoleType> types = this.roleTypeDao.GetRoleTypeList();

		for (UserGroup item : list) {
			UserGroupTree node = new UserGroupTree();
			node.setUid(item.getUid());
			node.setName(item.getName());
			node.setChildren(new ArrayList<UserGroupTree>());
			node.setRoleType(-1);
			node.setRoleTypeName("");
			node.setTreeType(0);

			this.SetChildren(node, types);

			tree.add(node);
		}

		return tree;
	}

	private RoleType GetRoleTypeByUID(int id, List<RoleType> types) {
		RoleType rt = null;
		for (RoleType item : types) {
			if (item.getId() == id) {
				return item;
			}
		}
		return rt;
	}

	private void SetChildren(UserGroupTree node, List<RoleType> types) {
		List<UserGroupDetail> list = this.userGroupDetailDao
				.GetUserGroupDetailListByGroup(node.getUid());
		if (list != null && list.size() > 0) {
			for (UserGroupDetail item : list) {
				User u = this.userDao.GetOneUser(item.getUserUid());
				if (u == null)
					continue;

				RoleType rt = this.GetRoleTypeByUID(item.getRoleType(), types);

				UserGroupTree n = new UserGroupTree();
				n.setUid(item.getUid());

				n.setName(u.getEmployeename());
				n.setChildren(new ArrayList<UserGroupTree>());
				if (rt != null) {
					n.setRoleType(rt.getId());
					n.setRoleTypeName(rt.getName());
				}
				n.setTreeType(1);

				List<UserGroupTree> cs = node.getChildren();
				cs.add(n);
				node.setChildren(cs);
			}
		}
	}

	@CustomerTransactional
	public void CreateUserGroupDetail(List<UserGroupDetail> list) {
		// TODO Auto-generated method stub
		for (UserGroupDetail item : list) {
			this.userGroupDetailDao.CreateUserGroupDetail(item);
		}
	}

	public List<UserGroupRoleTypeTree> GetUserGroupRoleTypeByTree(String name,
			String userUid) {
		// TODO Auto-generated method stub
		List<UserGroup> groups = this.GetUserGroupList(name);
		List<RoleType> types = this.roleTypeDao.GetRoleTypeList();
		List<UserGroupDetail> details = this.userGroupDetailDao
				.GetUserGroupDetailListByUser(userUid);

		List<UserGroupRoleTypeTree> tree = new ArrayList<UserGroupRoleTypeTree>();
		for (UserGroup item : groups) {
			UserGroupRoleTypeTree node = new UserGroupRoleTypeTree();
			node.setUid(item.getUid());
			node.setName(item.getName());
			node.setChecked(false);
			node.setId(-1);
			node.setUserUid(null);
			node.setChildren(new ArrayList<UserGroupRoleTypeTree>());

			this.SetRoleTypeList(node, types, details);

			tree.add(node);
		}
		return tree;
	}

	private void SetRoleTypeList(UserGroupRoleTypeTree node,
			List<RoleType> types, List<UserGroupDetail> details) {
		List<UserGroupRoleTypeTree> children = new ArrayList<UserGroupRoleTypeTree>();
		for (RoleType item : types) {
			UserGroupRoleTypeTree n = new UserGroupRoleTypeTree();
			n.setUid(UUID.randomUUID().toString());
			n.setName(item.getName());
			n.setId(item.getId());
			n.setUserUid(null);
			n.setChildren(new ArrayList<UserGroupRoleTypeTree>());
			n.setChecked(false);

			for (UserGroupDetail it : details) {
				if (it.getRoleType() == item.getId()
						&& it.getUserGroupUid().equals(node.getUid())) {
					n.setChecked(true);
					n.setUserUid(it.getUserUid());
					n.setUid(it.getUid());
				}
			}

			children.add(n);
		}

		node.setChildren(children);
	}

	@CustomerTransactional
	public void SaveUserGroupDetailByUser(String userUid, GroupRoleType[] ary) {
		// TODO Auto-generated method stub
		for (GroupRoleType item : ary) {
			this.userGroupDetailDao.DeleteUserGroupDetailByUserAndGroup(
					userUid, item.getGroupuid());
			for (int it : item.getRoletypes()) {
				UserGroupDetail de = new UserGroupDetail();
				de.setUid(UUID.randomUUID().toString());
				de.setUserUid(userUid);
				de.setUserGroupUid(item.getGroupuid());
				de.setRoleType(it);

				this.userGroupDetailDao.CreateUserGroupDetail(de);
			}
		}
	}
}
