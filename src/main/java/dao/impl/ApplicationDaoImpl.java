package dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import mappers.ApplicationMapper;
import model.Application;
import dao.ApplicationDao;

@Repository("application")
public class ApplicationDaoImpl implements ApplicationDao {

	@Resource
	private ApplicationMapper applicationMapper;

	public List<Application> GetApplicationList(String name, String typeUid,
			int showInMenu, int forbidden) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();

		if (name == null)
			name = "";
		name = "%" + name + "%";
		params.put("name", name);

		params.put("typeUid", typeUid);
		params.put("showInMenu", showInMenu);
		params.put("forbidden", forbidden);

		return this.applicationMapper.GetApplicationList(params);
	}

	public Application GetApplication(String uid) {
		// TODO Auto-generated method stub
		return this.applicationMapper.GetApplication(uid);
	}

	public void CreateApplication(Application application) {
		// TODO Auto-generated method stub
		this.applicationMapper.CreateApplication(application);
	}

	public void UpdateApplication(Application application) {
		// TODO Auto-generated method stub
		this.applicationMapper.UpdateApplication(application);
	}

	public void DeleteApplication(String uid) {
		// TODO Auto-generated method stub
		this.applicationMapper.DeleteApplication(uid);
	}

	public List<Application> GetApplicationListByNotForbidden() {
		// TODO Auto-generated method stub
		return this.applicationMapper.GetApplicationListByNotForbidden();
	}

	public List<Application> GetUserApplications(String userName) {
		// TODO Auto-generated method stub
		return this.applicationMapper.GetUserApplications(userName);
	}

}
