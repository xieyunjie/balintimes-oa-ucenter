package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import model.Application;
import model.ApplicationRole;
import model.RoleApplication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.ApplicationService;
import service.RoleApplicationService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("application")
public class ApplicationController extends BaseController {

	@Resource
	private ApplicationService applicationService;
	@Resource
	private RoleApplicationService roleApplicationService;

	@RequestMapping("list")
	@ResponseBody
	public String GetApplicationList(String name, String typeUid,
			int showInMenu, int forbidden) {
		return JsonUtil.ResponseSuccessfulMessage(this.applicationService
				.GetApplicationList(name, typeUid, showInMenu, forbidden));
	}

	@RequestMapping("listByRole/{roleuid}")
	@ResponseBody
	public String GetApplicationListByRole(@PathVariable String roleuid) {
		List<Application> apps = this.applicationService
				.GetApplicationListByNotForbidden();
		List<RoleApplication> roleApps = this.roleApplicationService
				.GetRoleApplicationListByRole(roleuid);
		List<ApplicationRole> appRs = new ArrayList<ApplicationRole>();
		for (Application item : apps) {
			ApplicationRole ar = new ApplicationRole();
			ar.setUid(item.getUid());
			ar.setName(item.getName());
			ar.setComment(item.getComment());
			ar.setCreateBy(item.getCreateBy());
			ar.setCreateTime(item.getCreateTime());
			ar.setEditBy(item.getEditBy());
			ar.setEditTime(item.getEditTime());
			ar.setForbidden(item.isForbidden());
			ar.setIconUrl(item.getIconUrl());
			ar.setShowInMenu(item.isShowInMenu());
			ar.setTypeName(item.getTypeName());
			ar.setTypeUid(item.getTypeUid());
			ar.setUrl(item.getUrl());

			for (RoleApplication it : roleApps) {
				if (it.getAppUid().equals(item.getUid())) {
					ar.setChecked(true);
				}
			}

			appRs.add(ar);
		}
		return JsonUtil.ResponseSuccessfulMessage(appRs);
	}

	@RequestMapping("getAllList")
	@ResponseBody
	public String GetApplicationListByAll() {
		return JsonUtil.ResponseSuccessfulMessage(this.applicationService
				.GetApplicationList(null, null, -1, -1));
	}

	@RequestMapping("listByNotForbidden")
	@ResponseBody
	public String GetApplicationListByNotForbidden() {
		return JsonUtil.ResponseSuccessfulMessage(this.applicationService
				.GetApplicationListByNotForbidden());
	}

	@RequestMapping("getApplication/{uid}")
	@ResponseBody
	public String GetApplication(@PathVariable String uid) {
		return JsonUtil.ResponseSuccessfulMessage(this.applicationService
				.GetApplication(uid));
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String CreateApplication(Application application) {
		application.setUid(UUID.randomUUID().toString());
		application
				.setCreateBy(this.webUsrUtil.CurrentUser().getEmployeeName());
		this.applicationService.CreateApplication(application);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateApplication(Application application) {
		application.setEditBy(this.webUsrUtil.CurrentUser().getEmployeeName());
		this.applicationService.UpdateApplication(application);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String DeleteApplication(String uid) {
		this.applicationService.DeleteApplication(uid);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}
}
