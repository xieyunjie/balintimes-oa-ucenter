package controller.base;

import java.util.List;

import javax.annotation.Resource;

import model.base.CustomerCategory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.base.CustomerCategoryService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("customercategory")
public class CustomerCategoryController extends BaseController {

	@Resource
	private CustomerCategoryService customerCategoryService;

	@RequestMapping("list")
	@ResponseBody
	public String GetCustomerCategoryList() {
		return this.GetCustomerCategoryList(null);
	}

	@RequestMapping("list/{name}")
	@ResponseBody
	public String GetCustomerCategoryList(@PathVariable String name) {
		List<CustomerCategory> list = this.customerCategoryService
				.GetCustomerCategoryList(name);
		return JsonUtil.ResponseSuccessfulMessage(list);
	}

	@RequestMapping("getcategory/{uid}")
	@ResponseBody
	public String GetCustomerCategory(@PathVariable String uid) {
		CustomerCategory model = this.customerCategoryService
				.GetCustomerCategory(uid);
		return JsonUtil.ResponseSuccessfulMessage(model);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String CreateCustomerCategory(CustomerCategory customerCategory) {
		this.customerCategoryService.CreateCustomerCategory(customerCategory);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateCustomerCategory(CustomerCategory customerCategory) {
		this.customerCategoryService.UpdateCustomerCategory(customerCategory);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String DeleteCustomerCategory(String uid) {
		this.customerCategoryService.DeleteCustomerCategory(uid);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

}
