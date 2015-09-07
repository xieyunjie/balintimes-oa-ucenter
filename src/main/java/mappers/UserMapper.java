package mappers;

import java.util.List;
import java.util.Map;

import model.User;

public interface UserMapper {
	User getUser(String uid);

	void createUser(User user);

	List<User> GetUserList();

	List<String> CheckUserName(String username);
	
	void deleteUserPost(String useruid);

	List<String> CheckEmployeeName(String employeename);

	void updateUser(User user);

	void deleteUser(Map<String, Object> params);

	String getUserPassword(String username);

	User getUserByName(String username);

	List<User> GetUserByPage(Map<String, Object> params);

	int GetUserTotalCount(Map<String, Object> params);

	void UpdatePassword(Map<String, Object> params);

	void UpdateLastLogin(String username);

	List<User> Pro_UserList(Map<String, Object> params);

	List<User> GetUserTreeList();

	List<User> GetUserTreeSet(String employeeName);

	User GetOneUser(String uid);

	User GetOneUserParent(String partentuid);

	void updateUserPost(User user);

	void createUserPost(User user);

	User GetEmployee(String username);

	List<User> GetSuperiors(String username);

	List<User> GetSubordinates(String username);

	List<User> GetUserByEmpName(String empName);
    
    List<User> GetUserTreeListByCondition(Map<String, Object> params);
}
