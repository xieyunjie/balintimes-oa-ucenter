package dao;

import java.util.List;

import model.UserGroup;

public interface UserGroupDao {
	List<UserGroup> GetUserGroupList(String name);

	UserGroup GetUserGroup(String uid);

	void CreateUserGroup(UserGroup userGroup);

	void UpdateUserGroup(UserGroup userGroup);

	void DeleteUserGroup(String uid);
}
