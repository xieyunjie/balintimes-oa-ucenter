package controller;

import java.util.UUID;

import javax.annotation.Resource;

import model.Application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.ApplicationService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("application")
public class ApplicationController extends BaseController {

	@Resource
	private ApplicationService applicationService;

	@RequestMapping("list")
	@ResponseBody
	public String GetApplicationList(String name, String typeUid,
			int showInMenu, int forbidden) {
		return JsonUtil.ResponseSuccessfulMessage(this.applicationService
				.GetApplicationList(name, typeUid, showInMenu, forbidden));
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
		return JsonUtil.ResponseSuccessfulMessage(this.applicationService.GetApplicationListByNotForbidden());
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
