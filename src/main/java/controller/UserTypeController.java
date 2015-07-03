package controller;

import java.util.List;

import javax.annotation.Resource;

import model.UserType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.UserTypeService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("usertype")
public class UserTypeController extends BaseController
{
	@Resource
	private UserTypeService userTypeService;

	@RequestMapping("list")
	@ResponseBody
	public String GetUserType()
	{
		List<UserType> list = userTypeService.GetUserType();
		
		return JsonUtil.ResponseSuccessfulMessage(list);
	}

}
