package service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.RoleApplicationDao;
import model.RoleApplication;
import service.RoleApplicationService;

@Service
public class RoleApplicationServiceImpl implements RoleApplicationService {

	@Resource
	private RoleApplicationDao roleApplicationDao;

	public List<RoleApplication> GetRoleApplicationListByRole(String roleUid) {
		// TODO Auto-generated method stub
		return this.roleApplicationDao.GetRoleApplicationListByRole(roleUid);
	}

}
