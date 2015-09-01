package dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import mappers.UserGroupDetailMapper;
import model.UserGroupDetail;
import dao.UserGroupDetailDao;

@Repository
public class UserGroupDetailDaoImpl implements UserGroupDetailDao {

	@Resource
	private UserGroupDetailMapper userGroupDetailMapper;

	public List<UserGroupDetail> GetUserGroupDetailListByGroup(String groupUid) {
		// TODO Auto-generated method stub
		return this.userGroupDetailMapper
				.GetUserGroupDetailListByGroup(groupUid);
	}

	public List<UserGroupDetail> GetUserGroupDetailListByUser(String userUid) {
		// TODO Auto-generated method stub
		return this.userGroupDetailMapper.GetUserGroupDetailListByUser(userUid);
	}

	public void CreateUserGroupDetail(UserGroupDetail userGroupDetail) {
		// TODO Auto-generated method stub
		this.userGroupDetailMapper.CreateUserGroupDetail(userGroupDetail);
	}

	public void DeleteUserGroupDetail(String uid) {
		// TODO Auto-generated method stub
		this.userGroupDetailMapper.DeleteUserGroupDetail(uid);
	}

	public void DeleteUserGroupDetailByGroup(String groupUid) {
		// TODO Auto-generated method stub
		this.userGroupDetailMapper.DeleteUserGroupDetailByGroup(groupUid);
	}

	public void DeleteUserGroupDetailByUser(String userUid) {
		// TODO Auto-generated method stub
		this.userGroupDetailMapper.DeleteUserGroupDetailByUser(userUid);
	}

	public int ExistsUserGroupDetail(String userGroupUid, String userUid,
			int roleType) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userGroupUid", userGroupUid);
		map.put("userUid", userUid);
		map.put("roleType", roleType);
		return this.userGroupDetailMapper.ExistsUserGroupDetail(map);
	}

	public void DeleteUserGroupDetailByUserAndGroup(String userUid,
			String userGroupUid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userGroupUid", userGroupUid);
		map.put("userUid", userUid);
		this.userGroupDetailMapper.DeleteUserGroupDetailByUserAndGroup(map);
	}

}
