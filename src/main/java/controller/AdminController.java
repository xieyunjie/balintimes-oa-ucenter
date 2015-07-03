package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.JsonUtil;

@Controller
@RequestMapping("admin")
public class AdminController
{

	@RequestMapping("index")
	public String toIndex()
	{
		return "admin/index";
	}

	@RequestMapping("getadminuser")
	@ResponseBody
	public String GetAdminUser()
	{
		return JsonUtil.ResponseSuccessfulMessage("获取了一个 admin 的 user");
	}
}
