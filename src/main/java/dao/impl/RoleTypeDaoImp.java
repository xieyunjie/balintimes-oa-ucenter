package dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import mappers.RoleTypeMapper;
import model.RoleType;
import dao.RoleTypeDao;

@Repository
public class RoleTypeDaoImp implements RoleTypeDao {

	@Resource
	private RoleTypeMapper roleTypeMapper;

	public List<RoleType> GetRoleTypeList() {
		// TODO Auto-generated method stub
		return this.roleTypeMapper.GetRoleTypeList();
	}

}
