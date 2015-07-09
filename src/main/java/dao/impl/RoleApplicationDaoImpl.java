package dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import mappers.RoleApplicationMapper;
import model.RoleApplication;
import dao.RoleApplicationDao;

@Repository
public class RoleApplicationDaoImpl implements RoleApplicationDao {

	@Resource
	private RoleApplicationMapper roleApplicationMapper;

	public List<RoleApplication> GetRoleApplicationListByRole(String roleUid) {
		// TODO Auto-generated method stub
		return this.roleApplicationMapper.GetRoleApplicationListByRole(roleUid);
	}

	public int GetRoleApplicationCountByRole(String roleUid, String appUid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleUid", roleUid);
		map.put("appUid", appUid);
		return this.roleApplicationMapper.GetRoleApplicationCountByRole(map);
	}

	public void CreateRoleApplication(RoleApplication roleApplication) {
		// TODO Auto-generated method stub
		this.roleApplicationMapper.CreateRoleApplication(roleApplication);
	}

	public void DeleteRoleApplicationByRoleAndApp(String roleUid, String appUid) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("roleUid", roleUid);
		map.put("appUid", appUid);
		this.roleApplicationMapper.DeleteRoleApplicationByRoleAndApp(map);
	}

	public void DeleteRoleApplicationByRole(String roleUid) {
		// TODO Auto-generated method stub
		this.roleApplicationMapper.DeleteRoleApplicationByRole(roleUid);
	}

	public void DeleteRoleApplicationByApp(String appUid) {
		// TODO Auto-generated method stub
		this.roleApplicationMapper.DeleteRoleApplicationByApp(appUid);
	}

}
