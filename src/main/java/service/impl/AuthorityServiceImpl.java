package service.impl;

import java.util.ArrayList;
import java.util.List;

import model.Application;
import model.Post;
import model.Resource;
import model.Role;
import model.User;
import model.authority.Employee;
import model.authority.EmployeePost;

import org.springframework.stereotype.Service;

import service.AuthorityService;
import dao.ApplicationDao;
import dao.PostDao;
import dao.ResourceDao;
import dao.RoleDao;
import dao.UserDao;

/**
 * Created by AlexXie on 2015/7/31.
 */

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@javax.annotation.Resource
	private ApplicationDao applicationDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private RoleDao roleDao;
	@javax.annotation.Resource
	private UserDao userDao;
	@javax.annotation.Resource
	private PostDao postDao;

	public List<Application> GetUserApplications(String username) {
		User user = this.userDao.getUserByName(username);
		if (!user.isIsadmin()) {
			return this.applicationDao.GetUserApplications(username);
		} else {
			return this.applicationDao.GetApplicationListByNotForbidden();
		}
	}

	private void SetResource(List<Resource> list, Resource item) {
		if (!item.getParentUid().equals("00000000-0000-0000-0000-000000000000")) {
			Resource r = this.resourceDao.GetResource(item.getParentUid());
			if (r.isForbidden() == false) {
				this.SetResource(list, r);
			}
		}
		if (!this.ExistsResourceByList(list, item)) {
			list.add(item);
		}
	}

	private boolean ExistsResourceByList(List<Resource> list, Resource item) {
		boolean b = false;
		for (Resource it : list) {
			if (it.getUid().equals(item.getUid())) {
				b = true;
			}
		}
		return b;
	}

	public List<Resource> GetUserMenu(String username, String applicationuid) {
		User user = this.userDao.getUserByName(username);
		List<Resource> list = new ArrayList<Resource>();
		if (!user.isIsadmin()) {
			List<Resource> rs = this.resourceDao.GetUserMenu(username, applicationuid);
			for (Resource item : rs) {
				this.SetResource(list, item);
			}
			return list;
		} else {
			List<Resource> rs = this.resourceDao.GetResourceListByNotForbidden(applicationuid);
			for (Resource item : rs) {
				if (item.getResourceType() == 1) {
					list.add(item);
				}
			}
			return list;
		}
	}

	public List<Resource> GetUserPermissions(String username, String applicationuid) {
		User user = this.userDao.getUserByName(username);
		if (!user.isIsadmin()) {
			return this.resourceDao.GetUserPermissions(username, applicationuid);
		} else {
			List<Resource> list = new ArrayList<Resource>();
			List<Resource> rs = this.resourceDao.GetResourceListByNotForbidden(applicationuid);
			for (Resource item : rs) {
				if (item.getResourceType() == 2) {
					list.add(item);
				}
			}
			return list;
		}
	}

	public List<Resource> GetUserMenuPermissions(String username, String menuuid) {
		return this.resourceDao.GetUserMenuPermissions(username, menuuid);
	}

	public List<Role> GetUserRoles(String username) {
		return this.roleDao.GetUserRoles(username);
	}

	public Employee GetEmployee(String username) {
		User user = userDao.GetEmployee(username);
		Employee employee = new Employee();
		employee.setUid(user.getUid());
		employee.setUsername(user.getUsername());
		employee.setIsadmin(user.isIsadmin());
		employee.setEmployeename(user.getEmployeename());
		employee.setEmail(user.getEmail());
		employee.setSex(user.getSex() == 1 ? "男" : "女");
		employee.setLastlogin(user.getLastlogin());
		employee.setAvatarurl("".equals(user.getAvatarurl()) == false ? "/resources/image/avatars/DefaultAvatar.jpg" : user.getAvatarurl());
		employee.setRootdeep(0);

		List<Post> posts = postDao.GetPostByEmployee(user.getUid());
		List<EmployeePost> employeePosts = new ArrayList<EmployeePost>();
		for (Post post : posts) {
			EmployeePost employeePost = new EmployeePost();
			employeePost.setOrganizationname(post.getOrganizationname());
			employeePost.setOrganizationuid(post.getOrganizationuid());
			employeePost.setPostname(post.getName());
			employeePost.setPostuid(post.getUid());
			employeePosts.add(employeePost);
		}
		employee.setPosts(employeePosts);
		return employee;
	}

	public List<Employee> GetSuperiors(String username) {
		List<User> users = userDao.GetSuperiors(username);
		List<Post> allPosts = postDao.GetPostList();
		List<Employee> employees=new ArrayList<Employee>();
		for (User user : users) {
			Employee employee = new Employee();
			employee.setUid(user.getUid());
			employee.setUsername(user.getUsername());
			employee.setIsadmin(user.isIsadmin());
			employee.setEmployeename(user.getEmployeename());
			employee.setEmail(user.getEmail());
			employee.setSex(user.getSex() == 1 ? "男" : "女");
			employee.setLastlogin(user.getLastlogin());
			employee.setAvatarurl("".equals(user.getAvatarurl()) == false ? "/resources/image/avatars/DefaultAvatar.jpg" : user.getAvatarurl());

			List<Post> posts = postDao.GetPostByEmployee(user.getUid());
			
			for (Post post : posts) {
				int depth = 0;
				depth = GetSuperiorsRootDeep(allPosts,  post, depth);
				employee.setRootdeep(depth);
			}
			employees.add(employee);
		}
		
		return employees;
	}

	public List<Employee> GetSubordinates(String username) {
		List<User> users = userDao.GetSubordinates(username);
		List<Post> allPosts = postDao.GetPostList();
		List<Employee> employees=new ArrayList<Employee>();
		for (User user : users) {
			Employee employee = new Employee();
			employee.setUid(user.getUid());
			employee.setUsername(user.getUsername());
			employee.setIsadmin(user.isIsadmin());
			employee.setEmployeename(user.getEmployeename());
			employee.setEmail(user.getEmail());
			employee.setSex(user.getSex() == 1 ? "男" : "女");
			employee.setLastlogin(user.getLastlogin());
			employee.setAvatarurl("".equals(user.getAvatarurl()) == false ? "/resources/image/avatars/DefaultAvatar.jpg" : user.getAvatarurl());

			List<Post> posts = postDao.GetPostByEmployee(user.getUid());
			
			for (Post post : posts) {
				int depth = 0;
				depth = GetSuperiorsRootDeep(allPosts,  post, depth);
				employee.setRootdeep(depth);
			}
			employees.add(employee);
		}
		
		return employees;
	}

	private int GetSuperiorsRootDeep(List<Post> list, Post currentPost, int depth) {
		for (Post post : list) {
			if (post.getUid().equalsIgnoreCase(currentPost.getParentuid())) {				
				currentPost = post;				
				if (post.getUid() != "00000000-0000-0000-0000-000000000001")
					depth=GetSuperiorsRootDeep(list, currentPost, depth)+1;
				break;
			}
		}
		return depth;

	}
}
