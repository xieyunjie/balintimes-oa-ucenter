package dao;

import java.util.List;

import model.Application;

public interface ApplicationDao {
	List<Application> GetApplicationList(String name ,String typeUid,int showInMenu,int forbidden);
	
	List<Application> GetApplicationListByNotForbidden();
	
	List<Application> GetUserApplications(String userName);
	
	Application GetApplication(String uid);
	
	void CreateApplication(Application application);
	
	void UpdateApplication(Application application);
	
	void DeleteApplication(String uid);
}
