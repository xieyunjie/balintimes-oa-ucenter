package dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import mappers.UserGroupMapper;
import model.UserGroup;
import dao.UserGroupDao;

@Repository
public class UserGroupDaoImpl implements UserGroupDao {

	@Resource
	private UserGroupMapper userGroupMapper;

	public List<UserGroup> GetUserGroupList(String name) {
		// TODO Auto-generated method stub
		if (name == null)
			name = "";
		name = "%" + name + "%";
		return this.userGroupMapper.GetUserGroupList(name);
	}

	public UserGroup GetUserGroup(String uid) {
		// TODO Auto-generated method stub
		return this.userGroupMapper.GetUserGroup(uid);
	}

	public void CreateUserGroup(UserGroup userGroup) {
		// TODO Auto-generated method stub
		this.userGroupMapper.CreateUserGroup(userGroup);
	}

	public void UpdateUserGroup(UserGroup userGroup) {
		// TODO Auto-generated method stub
		this.userGroupMapper.UpdateUserGroup(userGroup);
	}

	public void DeleteUserGroup(String uid) {
		// TODO Auto-generated method stub
		this.userGroupMapper.DeleteUserGroup(uid);
	}

}
