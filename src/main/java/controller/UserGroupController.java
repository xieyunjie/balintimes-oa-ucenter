package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import model.GroupRoleType;
import model.RoleType;
import model.UserGroup;
import model.UserGroupDetail;
import model.UserGroupRoleTypeTree;
import model.UserGroupTree;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.RoleTypeService;
import service.UserGroupService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("usergroup")
public class UserGroupController extends BaseController {

	@Resource
	private UserGroupService userGroupService;
	@Resource
	private RoleTypeService roleTypeService;

	// @RequestMapping("list")
	// @ResponseBody
	// public String GetUserGroupList() {
	// return this.GetUserGroupList(null);
	// }
	//
	// @RequestMapping("list/{name}")
	// @ResponseBody
	// public String GetUserGroupList(@PathVariable String name) {
	// List<UserGroup> list = this.userGroupService.GetUserGroupList(name);
	// return JsonUtil.ResponseSuccessfulMessage(list);
	// }

	@RequestMapping("roletypelist")
	@ResponseBody
	public String GetRoleTypeList() {
		List<RoleType> list = this.roleTypeService.GetRoleTypeList();
		return JsonUtil.ResponseSuccessfulMessage(list);
	}

	@RequestMapping("list")
	@ResponseBody
	public String GetUserGroupList() {
		return this.GetUserGroupList(null);
	}

	@RequestMapping("list/{name}")
	@ResponseBody
	public String GetUserGroupList(@PathVariable String name) {
		List<UserGroupTree> tree = this.userGroupService
				.GetUserGroupByTree(name);
		return JsonUtil.ResponseSuccessfulMessage(tree);
	}

	@RequestMapping("getusergroup/{uid}")
	@ResponseBody
	public String GetUserGroup(@PathVariable String uid) {
		UserGroup model = this.userGroupService.GetUserGroup(uid);
		return JsonUtil.ResponseSuccessfulMessage(model);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String CreateUserGroup(UserGroup userGroup) {
		this.userGroupService.CreateUserGroup(userGroup);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateUserGroup(UserGroup userGroup) {
		this.userGroupService.UpdateUserGroup(userGroup);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String DeleteUserGroup(String uid) {
		this.userGroupService.DeleteUserGroup(uid);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "deletebygroup", method = RequestMethod.POST)
	@ResponseBody
	public String DeleteUserGroupDetailByGroup(String groupUid) {
		this.userGroupService.DeleteUserGroupDetailByGroup(groupUid);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "deletebyusergroupdetail", method = RequestMethod.POST)
	@ResponseBody
	public String DeleteUserGroupDetail(String userGroupDetailUid) {
		this.userGroupService.DeleteUserGroupDetail(userGroupDetailUid);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "createusergroupdetail", method = RequestMethod.POST)
	@ResponseBody
	public String CreateUserGroupDetail(String userUids, String userGroupUid,
			int roleType) {
		List<UserGroupDetail> list = new ArrayList<UserGroupDetail>();
		String[] ary = userUids.split(",");
		for (String item : ary) {
			UserGroupDetail de = new UserGroupDetail();
			de.setUid(UUID.randomUUID().toString());
			de.setUserUid(item);
			de.setUserGroupUid(userGroupUid);
			de.setRoleType(roleType);

			list.add(de);
		}

		this.userGroupService.CreateUserGroupDetail(list);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "getusergrouproletype", method = RequestMethod.POST)
	@ResponseBody
	public String GetUserGroupRoleTypeByTree(String name, String useruid) {
		List<UserGroupRoleTypeTree> list = this.userGroupService
				.GetUserGroupRoleTypeByTree(name, useruid);
		return JsonUtil.ResponseSuccessfulMessage(list);
	}

	@RequestMapping(value = "saveusergroupdetail", method = RequestMethod.POST)
	@ResponseBody
	public String SaveUserGroupDetailByUser(String useruid, String json) {
		GroupRoleType[] gAry = JsonUtil.ToObject(json, GroupRoleType[].class);
		this.userGroupService.SaveUserGroupDetailByUser(useruid, gAry);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

}
