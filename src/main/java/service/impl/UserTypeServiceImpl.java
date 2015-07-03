package service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.UserTypeDao;
import model.UserType;
import service.UserTypeService;

@Service("userTypeService")
public class UserTypeServiceImpl implements UserTypeService
{
	@Resource
	private UserTypeDao userTypeDao;

	public List<UserType> GetUserType()
	{
		return userTypeDao.GetUserType();
	}

}
