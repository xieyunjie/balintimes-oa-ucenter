package service;

import java.util.List;

import model.GroupRoleType;
import model.UserGroup;
import model.UserGroupDetail;
import model.UserGroupRoleTypeTree;
import model.UserGroupTree;

public interface UserGroupService {
	List<UserGroup> GetUserGroupList(String name);

	List<UserGroupTree> GetUserGroupByTree(String name);

	UserGroup GetUserGroup(String uid);

	void CreateUserGroup(UserGroup userGroup);

	void UpdateUserGroup(UserGroup userGroup);

	void DeleteUserGroup(String uid);

	void DeleteUserGroupDetailByGroup(String groupUid);

	void DeleteUserGroupDetail(String userGroupDetailUid);

	void CreateUserGroupDetail(List<UserGroupDetail> list);

	List<UserGroupRoleTypeTree> GetUserGroupRoleTypeByTree(String name,
			String userUid);

	void SaveUserGroupDetailByUser(String userUid, GroupRoleType[] ary);
}
