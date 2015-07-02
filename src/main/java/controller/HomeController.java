package controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shiro.WebUser;
import util.JsonUtil;
import util.WebUserUtil;

@Controller
@RequestMapping("home")
public class HomeController
{

	@Resource
	private WebUserUtil WebUserUtil;
	
	@RequestMapping("")
	public String toHome()
	{
		return "home";
	}

	@RequestMapping("inituser")
	@ResponseBody
	public String InitUser()
	{
		WebUser webUser = WebUserUtil.CurrentUser();

		return JsonUtil.ToJson(webUser);
	}

	@RequestMapping("usermodule")
	@ResponseBody
	public String UserModule()
	{

		return JsonUtil.ToJson(WebUserUtil.CurrentUser().getModules());
	}
	
	@RequestMapping("inituser2")
	@ResponseBody
	public WebUser InitUser2()
	{
		WebUser webUser = WebUserUtil.CurrentUser();
		return webUser;
	}

}
