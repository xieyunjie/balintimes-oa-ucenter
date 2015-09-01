package dao;

import java.util.List;

import model.UserGroupDetail;

public interface UserGroupDetailDao {
	List<UserGroupDetail> GetUserGroupDetailListByGroup(String groupUid);

	List<UserGroupDetail> GetUserGroupDetailListByUser(String userUid);

	int ExistsUserGroupDetail(String userGroupUid, String userUid, int roleType);

	void CreateUserGroupDetail(UserGroupDetail userGroupDetail);

	void DeleteUserGroupDetail(String uid);

	void DeleteUserGroupDetailByGroup(String groupUid);

	void DeleteUserGroupDetailByUser(String userUid);
	
	void DeleteUserGroupDetailByUserAndGroup(String userUid,String userGroupUid);
}
