package dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import mappers.RoleResourceMapper;
import model.RoleResource;
import dao.RoleResourceDao;

@Repository
public class RoleResourceDaoImpl implements RoleResourceDao {

	@Resource
	private RoleResourceMapper roleResourceMapper;

	public List<RoleResource> GetRoleResourceListByRole(String roleUid) {
		// TODO Auto-generated method stub
		return this.roleResourceMapper.GetRoleResourceListByRole(roleUid);
	}

	public void CreateRoleResource(RoleResource roleResource) {
		// TODO Auto-generated method stub
		this.roleResourceMapper.CreateRoleResource(roleResource);
	}

	public void DeleteRoleResourceByRoleAndResource(String roleUid,
			String resourceUid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleUid", roleUid);
		map.put("resourcesUid", resourceUid);

		this.roleResourceMapper.DeleteRoleResourceByRoleAndResource(map);
	}

	public void DeleteRoleResourceByRole(String roleUid) {
		// TODO Auto-generated method stub
		this.roleResourceMapper.DeleteRoleResourceByRole(roleUid);
	}

	public int GetRoleResourceCountByRoleAndApp(String roleUid, String appUid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleUid", roleUid);
		map.put("appUid", appUid);
		return this.roleResourceMapper.GetRoleResourceCountByRoleAndApp(map);
	}

	public void DeleteRoleResourceByResource(String resourceUid) {
		// TODO Auto-generated method stub
		this.roleResourceMapper.DeleteRoleResourceByResource(resourceUid);
	}

	public void DeleteRoleResourceByRoleAndApp(String roleUid, String appUid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleUid", roleUid);
		map.put("appUid", appUid);
		this.roleResourceMapper.DeleteRoleResourceByRoleAndApp(map);
	}

}
