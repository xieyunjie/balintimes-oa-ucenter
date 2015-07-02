package service.impl;

import java.util.List;

import javax.annotation.Resource;

import model.User;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Service;

import service.UserService;
import tuples.TuplePage;
import annotation.CustomerTransactional;
import dao.UserDao;

@Service("userService")
public class UserServiceImpl implements UserService
{
	@Resource
	private PasswordService passwordService;
	@Resource
	private UserDao userDao;

	@CustomerTransactional
	public boolean create(User user)
	{
		user.setPassword(passwordService.encryptPassword(user.getPassword()));
		return userDao.createUser(user);
	}

	public User getUser(String uid)
	{
		return userDao.getUser(uid);
	}

	public void throwEx()
	{
		throw new RuntimeException("alex xie exception!");
	}

	public List<User> GetUserList()
	{
		return userDao.GetUserList();
	}

	public void updateUser(User user)
	{
		this.userDao.updateUser(user);
	}

	public void deleteUser(String uid)
	{
		this.userDao.deleteUser(uid);
	}

	public String getUserPassword(String username)
	{
		return this.userDao.getUserPassword(username);
	}

	@Override
	public User getUserByName(String username)
	{
		return this.userDao.getUserByName(username);
	}

	@Override
	public TuplePage<List<User>, Integer> GetUserList(String username, String deptname, int page, int pageSize)
	{
		return this.userDao.GetUserList(username, deptname, page, pageSize);
	}
}
