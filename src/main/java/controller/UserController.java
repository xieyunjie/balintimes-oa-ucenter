package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import model.Post;
import model.User;
import model.UserTree;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.PostService;
import service.UserService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private PostService postService;

	@RequestMapping(value = "getuserbyempname", method = RequestMethod.POST)
	@ResponseBody
	public String GetUserListByEmpName(String empname) {
		if (empname != null && !empname.equals("")) {
			List<User> list = this.userService.GetUserByEmpName(empname);
			return JsonUtil.ResponseSuccessfulMessage(list);
		} else {
			return JsonUtil.ResponseSuccessfulMessage(new ArrayList<User>());
		}
	}

	@RequestMapping("list")
	@ResponseBody
	public String UserList() throws InterruptedException {
		// Thread.sleep(1 * 1000);

		// System.out.println(LocalMsg("name"));

		List<User> list = new ArrayList<User>();

		list = this.userService.GetUserList();

		return JsonUtil.ResponseSuccessfulMessage(list);
	}

	@RequestMapping("listbypage")
	@ResponseBody
	public String UserListforPage(String username, String employeename, String usertype, String isenable, int page, int pageSize) {
		if (isenable != null) {
			isenable = isenable.equals("-1") ? "" : isenable;
		}

		tuples.TuplePage<List<User>, Integer> result = this.userService.GetUserList(username, employeename, usertype, isenable, page, pageSize);

		return JsonUtil.ResponseSuccessfulMessage(result.objectList, result.objectTotalCount);
	}

	@RequestMapping("getuser/{uid}")
	@ResponseBody
	public String GetUser(@PathVariable String uid) {
		User user;
		if (uid.equals("0")) {
			user = new User();
		} else {
			user = this.userService.getUser(uid);
		}

		return JsonUtil.ResponseSuccessfulMessage(user);
	}

	@RequestMapping("getuser")
	@ResponseBody
	public String GetSomnUser() {
		User user;
		user = new User();

		return JsonUtil.ResponseSuccessfulMessage(user);
	}

	@RequestMapping(value = "create")
	@ResponseBody
	public String createUser(User user) {
		// 相同用户名
		if (this.userService.ExistsUserName(user.getUsername()) == true) {
			return JsonUtil.ResponseFailureMessage("已经存在相同用户名，请重新录入。");
		}

		user.setUid(UUID.randomUUID().toString());
		user.setCreateby(webUsrUtil.CurrentUser().getEmployeeName());
		user.setCreatetime(new Date());

		this.userService.create(user);

		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public String updateUser(User user) {
		// 相同用户名
		if (this.userService.ExistsUserName(user.getUsername(), user.getUid()) == true) {
			return JsonUtil.ResponseFailureMessage("已经存在相同用户名，请重新录入。");
		}

		if (this.userService.ExistsEmployeeName(user.getEmployeename()) == true) {
			return JsonUtil.ResponseFailureMessage("已经存在相同员工名，请重新录入。");
		}
		user.setEditby(webUsrUtil.CurrentUser().getEmployeeName());
		user.setEdittime(new Date());
		this.userService.updateUser(user);
		return JsonUtil.ResponseSuccessfulMessage("修改成功");
	}

	@RequestMapping(value = "resetpassword")
	@ResponseBody
	public String ResetPassword(String UID) {
		this.userService.UpdatePassword(UID, "1");

		return JsonUtil.ResponseSuccessfulMessage("重置成功");
	}

	@RequestMapping(value = "updatepassword", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateUserPassword(String oldpassword, String newpassword) {

		try {
			String username = this.userService.UpdatePassword(webUsrUtil.CurrentUser().getUid(), oldpassword, newpassword);
			if (username.equals("")) {
				return JsonUtil.ResponseFailureMessage("修改密码错误！");
			}
			shiro.Utils.logout();
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, newpassword);
			subject.login(token);

			return JsonUtil.ResponseSuccessfulMessage("修改密码成功！");

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.ResponseFailureMessage("修改密码异常!!" + e.getMessage());
		}

	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(String UID) {
		System.out.println("*******" + UID);
		this.userService.deleteUser(UID, webUsrUtil.CurrentUser().getEmployeeName());

		return JsonUtil.ResponseSuccessfulMessage("删除成功");
	}

	@RequestMapping("getnulluser")
	@ResponseBody
	public String GetNullUser() {
		return JsonUtil.ResponseSuccessfulMessage("获取了一个 null 的 user");
	}

	@RequestMapping("getresourcepermission")
	@ResponseBody
	public String GetResourcePermission(String state) {

		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (state.equals("auth/user")) {
			return JsonUtil.ResponsePermissionMessage(false, "无权限");
		} else {
			return JsonUtil.ResponsePermissionMessage(true, "");
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("tree")
	@ResponseBody
	public String GetUserTreeList() {
		List<User> listUsers = new ArrayList<User>();
		listUsers = userService.GetUserTreeList();

		List<Post> posts = new ArrayList<Post>();
		posts = postService.GetPostList();

		List<User> users = new ArrayList<User>();
		users = this.FillUserListWithVacantPost(listUsers, posts);
		Collections.sort(users);

		List<UserTree> trees = InitUserTree(users);
		return JsonUtil.ResponseSuccessfulMessage(trees);
	}

	// @RequestMapping(value = "tree/{employeeName}", method =
	// RequestMethod.GET)
	// @ResponseBody
	// public String GetUserTreeSet(@PathVariable String employeeName) {
	// List<User> listUsersSet = userService.GetUserTreeSet(employeeName);
	// List<UserTree> treesSet = InitUserTree(listUsersSet);
	// return JsonUtil.ResponseSuccessfulMessage(treesSet);
	// }

	@SuppressWarnings("unchecked")
	@RequestMapping("querytree")
	@ResponseBody
	public String GetUserTreeListByCondition(String username, String employeename, String postuid, String postname, String organizationuid, String orgnizationname) {
		List<UserTree> trees = new ArrayList<UserTree>(1000);
		List<User> listUsers = userService.GetUserTreeListByCondition(username, employeename, postuid, organizationuid);

		if ((username == null || username.equalsIgnoreCase("")) && (employeename == null || employeename.equalsIgnoreCase("")) 
				&& (postuid == null || postuid.equalsIgnoreCase("")) && (organizationuid == null || organizationuid.equalsIgnoreCase("")))
			return this.GetUserTreeList();
		
		if (listUsers.size() > 0) {
			postuid = listUsers.get(0).getPostuid();

			if (organizationuid == null)
				organizationuid = "";

			List<Post> posts = postService.GetPostParent(postuid, organizationuid);

			List<User> allUsers = userService.GetUserTreeList();

			List<User> users = this.FillUserListWithVacantPost(allUsers, posts);
			Collections.sort(users);

			trees = InitUserTree(users);
		} else {
			return JsonUtil.ResponseSuccessfulMessage("");
		}
		
		return JsonUtil.ResponseSuccessfulMessage(trees);
	}

	// 根据职位树生成用户树，没有用户的职位同样显示
	private List<User> FillUserListWithVacantPost(List<User> list, List<Post> posts) {
		boolean isFind = false;
		List<User> resultUsers = new ArrayList<User>();

		for (Post post : posts) {
			for (User user : list) {
				if (user.getPostuid().equalsIgnoreCase(post.getUid())) {
					User itemUser = user;
					itemUser.setPostname(post.getName());
					itemUser.setParentuid(post.getParentuid());
					itemUser.setParentname(post.getParentname());
					itemUser.setOrganizationuid(post.getOrganizationuid());
					itemUser.setOrganizationname(post.getOrganizationname());
					resultUsers.add(itemUser);
					isFind = true;
				}
			}
			if (isFind == true) {
				isFind = false;
				continue;
			} else {
				User tempUser = new User();
				tempUser.setUid(UUID.randomUUID().toString());
				tempUser.setPostuid(post.getUid());
				tempUser.setPostname(post.getName());
				tempUser.setParentuid(post.getParentuid());
				tempUser.setUsername("暂无用户");
				tempUser.setEmployeename(post.getName() + "(暂无员工)");
				tempUser.setParentuid(post.getParentuid());
				tempUser.setOrganizationuid(post.getOrganizationuid());
				tempUser.setOrganizationname(post.getOrganizationname());
				resultUsers.add(tempUser);
			}

		}
		return resultUsers;
	}

	private List<UserTree> InitUserTree(List<User> list) {
		List<UserTree> trees = new ArrayList<UserTree>();
		if (list == null) {
			return trees;
		}
		if (list.size() < 1) {
			return trees;
		}

		String rootUID = "";
		UserTree rootUser = null;
		rootUID = "00000000-0000-0000-0000-000000000001";
		for (User user : list) {
			if (user.getPostuid().equalsIgnoreCase(rootUID)) {
				rootUser = new UserTree(user);
				rootUser.setChildren(this.GetChildren(list, rootUser.getPostuid(), rootUser.getPostname()));
				trees.add(rootUser);
				break;
			}
		}

		return trees;
	}

	private List<UserTree> GetChildren(List<User> list, String parentUID, String parentname) {

		List<UserTree> tree = new ArrayList<UserTree>();

		for (User user : list) {
			if (user.getParentuid().equalsIgnoreCase(parentUID)) {

				UserTree node = new UserTree(user);
				node.setParentname(parentname);
				node.setChildren(this.GetChildren(list, user.getPostuid(), node.getPostname()));
				tree.add(node);
			}
		}

		return tree;
	}

	@RequestMapping(value = "getoneuser/{uid}", method = RequestMethod.GET)
	@ResponseBody
	public String GetOneUser(@PathVariable String uid) {
		User oneUser = userService.GetOneUser(uid);
		return JsonUtil.ResponseSuccessfulMessage(oneUser);
	}

	@RequestMapping(value = "getoneuserparent/{parentuid}", method = RequestMethod.GET)
	@ResponseBody
	public String GetOneUserParent(@PathVariable String parentuid) {
		User oneUserParent = userService.GetOneUserParent(parentuid);
		return JsonUtil.ResponseSuccessfulMessage(oneUserParent);
	}

	// @Resource
	// public AuthorityService authorityService;

	// @RequestMapping(value = "teste", method = RequestMethod.GET)
	// @ResponseBody
	// public String TestEmployee(){
	// model.authority.Employee employee=authorityService.GetEmployee("qq");
	// return JsonUtil.ResponseSuccessfulMessage(employee);
	// }

	// @RequestMapping(value = "tests", method = RequestMethod.GET)
	// @ResponseBody
	// public String TestSuperiors() {
	// List<model.authority.Employee> employees =
	// authorityService.GetSuperiors("qq");
	// return JsonUtil.ResponseSuccessfulMessage(employees);
	// }

	// @RequestMapping(value = "testsub", method = RequestMethod.GET)
	// @ResponseBody
	// public String TestSubordinates() {
	// List<model.authority.Employee> employees =
	// authorityService.GetSubordinates("admin");
	// return JsonUtil.ResponseSuccessfulMessage(employees);
	// }
}
