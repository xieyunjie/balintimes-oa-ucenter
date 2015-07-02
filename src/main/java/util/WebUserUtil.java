package util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import model.Menu;
import model.Module;
import model.User;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import service.UserService;
import shiro.WebUser;

@Component
public class WebUserUtil
{
	private final String WEBUSER_KEY = "WEBUSER";

	@Resource
	private UserService userService;

	public WebUser CurrentUser()
	{
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() == false)
		{
			return null;
		}

		WebUser webUser = (WebUser) subject.getSession().getAttribute(WEBUSER_KEY);
		if (webUser == null)
		{
			webUser = initWebUser();
			subject.getSession().setAttribute(WEBUSER_KEY, webUser);
		}
		return webUser;
	}

	private WebUser initWebUser()
	{
		User u = userService.getUserByName(SecurityUtils.getSubject().getPrincipal().toString());

		WebUser webUser = new WebUser();
		webUser.setUsername(u.getUsername());
		webUser.setEmail("123@163.com");
		webUser.setEmployeeName(u.getUsername());

		webUser.setModules(InitModule());
		return webUser;
	}

	private static List<Module> InitModule()
	{
		List<Module> list = new ArrayList<Module>(3);

		Module m = new Module(UUID.randomUUID().toString(), "机构设置", "org");
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "机构管理", "organization", "/resources/controller/org/user.js"));
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "部门设置", "dept", "/resources/controller/org/dept.js"));
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "职位设置", "post", "/resources/controller/org/user.js"));
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "地区设置", "region", "/resources/controller/org/user.js"));
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "人员管理", "employee", "/resources/controller/org/user.js"));
		list.add(m);

		m = new Module(UUID.randomUUID().toString(), "权限设置", "auth");
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "用户管理", "user", "/resources/controller/auth/user.js"));
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "应用模块", "application", "/resources/controller/org/dept.js"));
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "角色管理", "roles", "/resources/controller/org/user.js"));
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "系统参数", "sysparams", "/resources/controller/org/user.js"));
		list.add(m);

		m = new Module(UUID.randomUUID().toString(), "其它设置", "other");
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "其它机构", "otherOrg", "/resources/controller/org/user.js"));
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "其它部门", "otherDept", "/resources/controller/org/dept.js"));
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "其它职位", "otherPost", "/resources/controller/org/user.js"));
		m.getMenus().add(new Menu(UUID.randomUUID().toString(), "其它地区", "otherRegion", "/resources/controller/org/user.js"));
		list.add(m);

		return list;
	}
}
