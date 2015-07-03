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

	public boolean ExistsUserName(String username)
	{
		return userDao.ExistsUserName(username);
	}

	public boolean ExistsUserName(String username, String uid)
	{
		return userDao.ExistsUserName(username, uid);
	}

	@CustomerTransactional
	public void updateUser(User user)
	{
		this.userDao.updateUser(user);
	}

	@CustomerTransactional
	public void deleteUser(String uid,String employeename)
	{
		this.userDao.deleteUser(uid,employeename);
	}

	public String getUserPassword(String username)
	{
		return this.userDao.getUserPassword(username);
	}

	public User getUserByName(String username)
	{
		return this.userDao.getUserByName(username);
	}

	public TuplePage<List<User>, Integer> GetUserList(String username, String deptname, int page, int pageSize)
	{
		return this.userDao.GetUserList(username, deptname, page, pageSize);
	}

	public void UpdatePassword(String uID, String password)
	{
		this.userDao.UpdatePassword(uID, passwordService.encryptPassword(password));

	}

}
