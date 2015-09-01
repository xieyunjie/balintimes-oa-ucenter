package mappers;

import java.util.List;
import java.util.Map;

import model.UserGroupDetail;

public interface UserGroupDetailMapper {
	List<UserGroupDetail> GetUserGroupDetailListByGroup(String groupUid);

	List<UserGroupDetail> GetUserGroupDetailListByUser(String userUid);

	int ExistsUserGroupDetail(Map<String, Object> map);

	void CreateUserGroupDetail(UserGroupDetail userGroupDetail);

	void DeleteUserGroupDetail(String uid);

	void DeleteUserGroupDetailByGroup(String groupUid);

	void DeleteUserGroupDetailByUser(String userUid);

	void DeleteUserGroupDetailByUserAndGroup(Map<String, Object> map);
}
