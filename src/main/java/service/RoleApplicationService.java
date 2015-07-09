package service;

import java.util.List;

import model.RoleApplication;

public interface RoleApplicationService {
	List<RoleApplication> GetRoleApplicationListByRole(String roleUid);
}
