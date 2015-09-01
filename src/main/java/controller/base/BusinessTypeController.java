package controller.base;

import java.util.List;

import javax.annotation.Resource;

import model.base.BusinessType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.base.BusinessTypeService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("businesstype")
public class BusinessTypeController extends BaseController {

	@Resource
	private BusinessTypeService businessTypeService;

	@RequestMapping("list")
	@ResponseBody
	public String GetBusinessTypeList() {
		return this.GetBusinessTypeList(null);
	}

	@RequestMapping("list/{name}")
	@ResponseBody
	public String GetBusinessTypeList(@PathVariable String name) {
		List<BusinessType> list = this.businessTypeService
				.GetBusinessTypeList(name);
		return JsonUtil.ResponseSuccessfulMessage(list);
	}

	@RequestMapping("gettype/{uid}")
	@ResponseBody
	public String GetBusinessType(@PathVariable String uid) {
		BusinessType model = this.businessTypeService.GetBusinessType(uid);
		return JsonUtil.ResponseSuccessfulMessage(model);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String CreateBusinessType(BusinessType businessType) {
		try {
			this.businessTypeService.CreateBusinessType(businessType);
			return JsonUtil.ResponseSuccessfulMessage("保存成功");
		} catch (Exception e) {
			return JsonUtil.ResponseFailureMessage(e.getMessage());
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateBusinessType(BusinessType businessType) {
		try {
			this.businessTypeService.UpdateBusinessType(businessType);
			return JsonUtil.ResponseSuccessfulMessage("保存成功");
		} catch (Exception e) {
			return JsonUtil.ResponseFailureMessage(e.getMessage());
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String DeleteBusinessType(String uid) {
		try {
			this.businessTypeService.DeleteBusinessType(uid);
			return JsonUtil.ResponseSuccessfulMessage("保存成功");
		} catch (Exception e) {
			return JsonUtil.ResponseFailureMessage(e.getMessage());
		}
	}

}
