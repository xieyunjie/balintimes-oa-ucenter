package dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import mappers.UserMapper;
import mappers.UserTypeMapper;
import model.UserType;
import dao.UserTypeDao;

@Repository("userTypeDao")
public class UserTypeDaoImpl implements UserTypeDao
{

	@Resource
	private UserTypeMapper userTypeMapper;

	public List<UserType> GetUserType()
	{
		return userTypeMapper.GetUserType();
	}

}
