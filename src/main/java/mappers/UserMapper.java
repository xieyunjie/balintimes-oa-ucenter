package mappers;

import java.util.List;
import java.util.Map;

import model.User;

public interface UserMapper
{
	public User getUser(String uid);

	public void createUser(User user);

	public List<User> GetUserList();

	public void updateUser(User user);

	public void deleteUser(String uid);

	public String getUserPassword(String username);

	public User getUserByName(String username);

	public List<User> GetUserByPage(Map<String, Object> params);

	public int GetUserTotalCount(Map<String, Object> params);
}
