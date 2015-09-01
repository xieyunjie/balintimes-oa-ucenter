package service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.RoleTypeDao;
import model.RoleType;
import service.RoleTypeService;

@Service
public class RoleTypeServiceImpl implements RoleTypeService {

	@Resource
	private RoleTypeDao roleTypeDao;

	public List<RoleType> GetRoleTypeList() {
		// TODO Auto-generated method stub
		return this.roleTypeDao.GetRoleTypeList();
	}

}
