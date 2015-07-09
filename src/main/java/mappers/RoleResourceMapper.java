package mappers;

import java.util.List;
import java.util.Map;

import model.RoleResource;

public interface RoleResourceMapper {
	List<RoleResource> GetRoleResourceListByRole(String roleUid);
	
	int GetRoleResourceCountByRoleAndApp(Map<String,Object> map);
	
	void CreateRoleResource(RoleResource roleResource);
	
	void  DeleteRoleResourceByRoleAndResource(Map<String, Object> map);
	
	void DeleteRoleResourceByRole(String roleUid);
	
	void DeleteRoleResourceByResource(String resourceUid);
}
