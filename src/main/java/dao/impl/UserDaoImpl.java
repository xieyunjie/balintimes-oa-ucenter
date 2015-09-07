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
public class UserDaoImpl implements UserDao {
	@Resource
	private UserMapper userMapper;

	public boolean createUser(User user) {
		userMapper.createUser(user);
//		userMapper.createUserPost(user);
		return true;
	}
	
	public boolean createUserPost(User user){
		userMapper.createUserPost(user);
		return true;
	}

	public List<User> GetUserList() {
		return userMapper.GetUserList();
	}

	public boolean ExistsUserName(String username) {
		List<String> uids = userMapper.CheckUserName(username);

		if (uids.size() > 0) {
			return true;
		}
		return false;
	}

	public User getUser(String uid) {
		return userMapper.getUser(uid);
	}

	public void updateUser(User user) {
		this.userMapper.updateUser(user);
		this.userMapper.deleteUserPost(user.getUid());
		this.userMapper.createUserPost(user);
	}

	public void deleteUser(String uid, String employeename) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("uid", uid);
		params.put("employeename", employeename);
		this.userMapper.deleteUser(params);
	}

	public String getUserPassword(String username) {
		return this.userMapper.getUserPassword(username);
	}

	public User getUserByName(String username) {
		return this.userMapper.getUserByName(username);
	}

	public TuplePage<List<User>, Integer> GetUserList(String username,
			String deptname, int page, int pageSize) {
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

	public TuplePage<List<User>, Integer> GetUserList(String username,
			String employeename, String usertype, String isenable, int page,
			int pageSize) {
		/*
		 * Map<String, Object> params = new HashMap<String, Object>(4);
		 * params.put("username", username); params.put("employeename",
		 * employeename); params.put("usertype", usertype);
		 * params.put("isenable", isenable);
		 * 
		 * int total = userMapper.GetUserTotalCount(params);
		 * 
		 * params.put("start", (page - 1) * pageSize); params.put("pageSize",
		 * pageSize); params.put("orderby", "createtime");
		 * 
		 * List<User> list = userMapper.GetUserByPage(params);
		 * 
		 * return new TuplePage<List<User>, Integer>(list, total);
		 */

		Map<String, Object> params = new HashMap<String, Object>(6);
		params.put("username", username);
		params.put("employeename", employeename);
		params.put("usertype", usertype);
		params.put("isenable", isenable);

		params.put("page", page);
		params.put("pageSize", pageSize);

		List<User> list = userMapper.Pro_UserList(params);

		return new TuplePage<List<User>, Integer>(list,
				(int) params.get("totalcount"));
	}

	public void UpdatePassword(String uID, String encryptPassword) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("uid", uID);
		params.put("password", encryptPassword);
		this.userMapper.UpdatePassword(params);
	}

	public boolean ExistsUserName(String username, String useruid) {
		boolean b = true;
		List<String> uids = userMapper.CheckUserName(username);

		if (uids.size() == 0) {
			b = false;
		}
		if (uids.size() == 1) {
			if (uids.get(0).equalsIgnoreCase(useruid)) {
				b = false;
			}
		}

		return b;

		// for (String uid : uids)
		// {
		// if (uid.equalsIgnoreCase(useruid))
		// {
		// return true;
		// }
		// }

	}

	public void UpdateLastLogin(String username) {
		this.userMapper.UpdateLastLogin(username);
	}

	public List<User> GetUserTreeList() {
		List<User> listUsers = userMapper.GetUserTreeList();
		return listUsers;
	}

	public List<User> GetUserTreeSet(String employeeName) {
		List<User> listUsersSet = userMapper.GetUserTreeSet(employeeName);
		return listUsersSet;
	}

	public User GetOneUser(String uid) {
		User oneUser = userMapper.GetOneUser(uid);
		return oneUser;
	}

	public User GetOneUserParent(String partentuid) {
		User oneUserParent = userMapper.GetOneUserParent(partentuid);
		return oneUserParent;
	}

	public User GetEmployee(String username) {
		return userMapper.GetEmployee(username);
	}

	public List<User> GetSuperiors(String username) {
		return userMapper.GetSuperiors(username);
	}

	public List<User> GetSubordinates(String username) {
		// TODO Auto-generated method stub
		return userMapper.GetSubordinates(username);
	}

	public boolean ExistsEmployeeName(String employeename) {
		List<String> uids = userMapper.CheckEmployeeName(employeename);

		if (uids.size() > 0) {
			return true;
		}
		return false;
	}
	
	public List<User> GetUserTreeListByCondition(String username, String employeename,String postuid,String organizationuid) {
		Map<String,Object> params=new HashMap<String, Object>(4);
		params.put("username", username);
		params.put("employeename", employeename);
		params.put("postuid", postuid);
		params.put("organizationuid", organizationuid);
				
		List<User> userList=this.userMapper.GetUserTreeListByCondition(params);
		return userList;
	}

	public List<User> GetUserByEmpName(String empName) {
		// TODO Auto-generated method stub
		if (empName == null)
			empName = "";
		empName = "%" + empName + "%";
		return this.userMapper.GetUserByEmpName(empName);
	}
	
	public void deleteUserPost(String useruid) {
		// TODO Auto-generated method stub
		this.userMapper.deleteUserPost(useruid);
	}

	
	

}
