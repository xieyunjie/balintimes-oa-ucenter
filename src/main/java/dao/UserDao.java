package dao;

import java.util.List;

import model.User;
import tuples.TuplePage;

public interface UserDao
{

	User getUser(String uid);

	boolean createUser(User user);

	List<User> GetUserList();
	
	boolean ExistsUserName(String username);
	
	boolean ExistsUserName(String username,String useruid);

	void updateUser(User user);

	void deleteUser(String uid,String employeename);

	String getUserPassword(String username);

	User getUserByName(String username);

	TuplePage<List<User>, Integer> GetUserList(String username, String deptname, int page, int pageSize);

	void UpdatePassword(String uID, String encryptPassword);

}
