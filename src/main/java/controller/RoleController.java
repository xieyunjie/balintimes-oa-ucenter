package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import model.Role;
import model.RoleTree;
import model.UserRoles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.RoleResourceService;
import service.RoleService;
import service.UserRolesService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

	@Resource
	private RoleService roleService;
	@Resource
	private RoleResourceService roleResourceService;
	@Resource
	private UserRolesService userRolesService;

	@RequestMapping("list")
	@ResponseBody
	public String GetRoleList() {
		List<Role> list = this.roleService.GetRoleList();
		List<RoleTree> tree = new ArrayList<RoleTree>();
		this.SetTree(tree, list, "00000000-0000-0000-0000-000000000000", null);
		return JsonUtil.ResponseSuccessfulMessage(tree);
	}

	@RequestMapping("listByUser/{useruid}")
	@ResponseBody
	public String GetRoleListByUser(@PathVariable String useruid) {
		List<Role> list = this.roleService.GetRoleListByNotForbidden();
		List<RoleTree> tree = new ArrayList<RoleTree>();
		List<UserRoles> userRoles = this.userRolesService
				.GetUserRolesListByUser(useruid);
		this.SetTree(tree, list, "00000000-0000-0000-0000-000000000000",
				userRoles);
		return JsonUtil.ResponseSuccessfulMessage(tree);
	}

	private void SetTree(List<RoleTree> tree, List<Role> list,
			String parentUid, List<UserRoles> userRoles) {
		if (list == null || list.size() <= 0)
			return;
		for (Role item : list) {
			if (item.getParentUid().equals(parentUid)) {
				RoleTree node = new RoleTree();
				node.setUid(item.getUid());
				node.setName(item.getName());
				node.setParentUid(item.getParentUid());
				node.setForbidden(item.isForbidden());
				node.setComment(item.getComment());
				node.setCreateBy(item.getCreateBy());
				node.setCreateTime(item.getCreateTime());
				node.setEditBy(item.getEditBy());
				node.setEditTime(item.getEditTime());
				List<RoleTree> children = new ArrayList<RoleTree>();
				node.setChildren(children);

				if (userRoles != null && userRoles.size() > 0) {
					for (UserRoles it : userRoles) {
						if (item.getUid().equals(it.getRoleUid())) {
							node.setChecked(true);
						}
					}
				}

				this.SetTree(node.getChildren(), list, node.getUid(), userRoles);

				tree.add(node);
			}
		}
	}

	@RequestMapping("getRole/{uid}")
	@ResponseBody
	public String GetRole(@PathVariable String uid) {
		return JsonUtil
				.ResponseSuccessfulMessage(this.roleService.GetRole(uid));
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String CreateRole(Role role) {
		role.setUid(UUID.randomUUID().toString());
		role.setCreateBy(this.webUsrUtil.CurrentUser().getEmployeeName());
		if (role.getParentUid() == null || role.getParentUid().equals("")) {
			role.setParentUid("00000000-0000-0000-0000-000000000000");
		}

		this.roleService.CreateRole(role);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateRole(Role role) {
		role.setEditBy(this.webUsrUtil.CurrentUser().getEmployeeName());
		if (role.getParentUid() == null || role.getParentUid().equals("")) {
			role.setParentUid("00000000-0000-0000-0000-000000000000");
		}

		this.roleService.UpdateRole(role);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String DeleteRole(String uid) {
		this.roleService.DeleteRole(uid);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "saveRoleResource", method = RequestMethod.POST)
	@ResponseBody
	public String SaveRoleResource(String roleUid, String appUid,
			String resourceUid, boolean checked) {
		this.roleResourceService.SaveRoleResource(roleUid, appUid, resourceUid,
				checked);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "cleanSetting", method = RequestMethod.POST)
	@ResponseBody
	public String CleanSetting(String roleUid) {
		this.roleResourceService.CleanSetting(roleUid);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "saveUserRole", method = RequestMethod.POST)
	@ResponseBody
	public String SaveUserRole(String userUid, String roleUid, boolean checked) {
		this.userRolesService.SaveUserRoles(userUid, roleUid, checked);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "cleanUserSetting", method = RequestMethod.POST)
	@ResponseBody
	public String CleanUserSetting(String userUid) {
		this.userRolesService.CleanUserRoles(userUid);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}
}
