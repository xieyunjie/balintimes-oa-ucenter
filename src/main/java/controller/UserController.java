package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.UserService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("user")
public class UserController extends BaseController
{
	@Resource
	private UserService userService;

	@RequestMapping("")
	public String ToIndex()
	{
		return "auth/user/index";
	}

	@RequestMapping("list")
	@ResponseBody
	public String UserList() throws InterruptedException
	{
		Thread.sleep(1 * 1000);

		// System.out.println(LocalMsg("name"));

		List<User> list = new ArrayList<User>();

		list = this.userService.GetUserList();

		return JsonUtil.ResponseSuccessfulMessage(list);
	}

	@RequestMapping("listbypage")
	@ResponseBody
	public String UserListforPage(String username, String deptname, int page, int pageSize)
	{
		tuples.TuplePage<List<User>, Integer> result = this.userService.GetUserList(username, deptname, page, pageSize);

		return JsonUtil.ResponseSuccessfulMessage(result.objectList, result.objectTotalCount);
	}

	@RequestMapping("getuser/{uid}")
	@ResponseBody
	public String GetUser(@PathVariable String uid)
	{
		User user = null;
		if (uid.equals("0"))
		{
			user = new User();
		}
		else
		{
			user = this.userService.getUser(uid);
		}

		return JsonUtil.ResponseSuccessfulMessage(user);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String createUser(User user)
	{
		// 相同用户名
		if (this.userService.ExistsUserName(user.getUsername()) == true)
		{
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
	public String updateUser(User user)
	{
		// 相同用户名
		if (this.userService.ExistsUserName(user.getUsername(), user.getUid()) == true)
		{
			return JsonUtil.ResponseFailureMessage("已经存在相同用户名，请重新录入。");
		}

		user.setEditby(webUsrUtil.CurrentUser().getEmployeeName());
		user.setEdittime(new Date());
		this.userService.updateUser(user);
		return JsonUtil.ResponseSuccessfulMessage("修改成功");
	}

	@RequestMapping(value = "resetpassword")
	@ResponseBody
	public String ResetPassword(String UID)
	{
		this.userService.UpdatePassword(UID, "1");

		return JsonUtil.ResponseSuccessfulMessage("重置成功");
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(String UID)
	{
		this.userService.deleteUser(UID,webUsrUtil.CurrentUser().getEmployeeName());

		return JsonUtil.ResponseSuccessfulMessage("删除成功");
	}

	@RequestMapping("getnulluser")
	@ResponseBody
	public String GetNullUser()
	{
		return JsonUtil.ResponseSuccessfulMessage("获取了一个 null 的 user");
	}
}
