package dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mappers.UserMapper;
import model.User;

import org.springframework.stereotype.Repository;

import tuples.TuplePage;
import dao.UserDao;

@Repository("userdao")
public class UserDaoImpl implements UserDao
{
	@Resource
	private UserMapper userMapper;

	public boolean createUser(User user)
	{
		userMapper.createUser(user);

		return true;
	}

	public List<User> GetUserList()
	{
		return userMapper.GetUserList();
	}

	public User getUser(String uid)
	{
		return userMapper.getUser(uid);
	}

	public void updateUser(User user)
	{
		this.userMapper.updateUser(user);
	}

	public void deleteUser(String uid)
	{
		this.userMapper.deleteUser(uid);
	}

	public String getUserPassword(String username)
	{
		return this.userMapper.getUserPassword(username);
	}

	@Override
	public User getUserByName(String username)
	{
		return this.userMapper.getUserByName(username);
	}

	@Override
	public TuplePage<List<User>, Integer> GetUserList(String username, String deptname, int page, int pageSize)
	{
		Map<String, Object> params = new HashMap<String, Object>(4);
		params.put("username", username);
		params.put("deptname", deptname);

		int total = userMapper.GetUserTotalCount(params);

		params.put("start", (page - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("orderby", "createtime");

		List<User> list = userMapper.GetUserByPage(params);

		return new TuplePage<List<User>, Integer>(list, total);

	}
}
