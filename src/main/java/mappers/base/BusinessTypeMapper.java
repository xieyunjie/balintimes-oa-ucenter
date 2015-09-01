package mappers.base;

import java.util.List;

import model.base.BusinessType;

public interface BusinessTypeMapper {
	List<BusinessType> GetBusinessTypeList(String name);
	
	BusinessType GetBusinessType(String uid);
	
	void CreateBusinessType(BusinessType businessType);
	
	void UpdateBusinessType(BusinessType businessType);
	
	void DeleteBusinessType(String uid);
}
