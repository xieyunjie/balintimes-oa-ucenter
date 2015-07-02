package service;

import java.util.List;

import tuples.TuplePage;
import model.User;

public interface UserService
{
	boolean create(User user);

	User getUser(String uid);

	void throwEx(); 
	

	void updateUser(User user);

	void deleteUser(String uid);

	String getUserPassword(String username);

	User getUserByName(String username);

	TuplePage<List<User>, Integer> GetUserList(String username, String deptname, int page, int pageSize);

	List<User> GetUserList();
}
