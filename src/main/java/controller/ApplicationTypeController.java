package controller;

import java.util.UUID;

import javax.annotation.Resource;

import model.ApplicationType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.ApplicationTypeService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("applicationType")
public class ApplicationTypeController extends BaseController {
	
	@Resource
	private ApplicationTypeService applicationTypeService;
	
	@RequestMapping("list/{name}")
	@ResponseBody
	public String getApplicationTypeList(@PathVariable String name){
		return JsonUtil.ResponseSuccessfulMessage(this.applicationTypeService.getApplicationTypeList(name));
	}
	
	@RequestMapping("list")
	@ResponseBody
	public String getApplicationTypeList(){
		return JsonUtil.ResponseSuccessfulMessage(this.applicationTypeService.getApplicationTypeList(null));
	}
	
	@RequestMapping("getType/{uid}")
	@ResponseBody
	public String getApplicationType(@PathVariable String uid){
		return JsonUtil.ResponseSuccessfulMessage(this.applicationTypeService.getApplicationType(uid));
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
	public String createApplicationType(ApplicationType applicationType){
		applicationType.setUid(UUID.randomUUID().toString());
		this.applicationTypeService.createApplicationType(applicationType);
		//return JsonUtil.ResponseSuccessfulMessage("保存成功");
		return JsonUtil.ResponseSuccessfulMessage(applicationType);
	}
	
	@RequestMapping(value = "update",method=RequestMethod.POST)
    @ResponseBody
	public String updateApplicationType(ApplicationType applicationType){
		this.applicationTypeService.updateApplicationType(applicationType);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}
	
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
	public String deleteApplicationType(String uid){
    	this.applicationTypeService.deleteApplicationType(uid);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}
}
