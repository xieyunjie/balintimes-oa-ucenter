package mappers;

import java.util.List;
import java.util.Map;

import model.Application;

public interface ApplicationMapper {
	List<Application> GetApplicationList(Map<String,Object> params);
	
	List<Application> GetApplicationListByNotForbidden();
	
	List<Application> GetUserApplications(String userName);
	
	Application GetApplication(String uid);
	
	void CreateApplication(Application application);
	
	void UpdateApplication(Application application);
	
	void DeleteApplication(String uid);
}
