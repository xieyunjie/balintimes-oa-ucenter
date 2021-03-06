package dao.base;

import java.util.List;

import model.base.CustomerCategory;

public interface CustomerCategoryDao {
	List<CustomerCategory> GetCustomerCategoryList(String name);

	CustomerCategory GetCustomerCategory(String uid);

	void CreateCustomerCategory(CustomerCategory customerCategory);

	void UpdateCustomerCategory(CustomerCategory customerCategory);

	void DeleteCustomerCategory(String uid);
}
