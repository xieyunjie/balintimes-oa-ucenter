package service.base;

import java.util.List;

import model.base.BusinessType;

public interface BusinessTypeService {
	List<BusinessType> GetBusinessTypeList(String name);
	
	BusinessType GetBusinessType(String uid);
	
	void CreateBusinessType(BusinessType businessType);
	
	void UpdateBusinessType(BusinessType businessType);
	
	void DeleteBusinessType(String uid);
}
