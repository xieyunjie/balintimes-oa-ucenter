package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import model.Role;
import model.User;
import model.authority.Menu;
import model.authority.Permission;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import service.AuthorityService;
import service.UserService;
import shiro.WebUser;

@Component
public class WebUserUtil {
    private final String WEBUSER_KEY = "WEBUSER";
    private final String USERMENUS_KEY = "USERMENUS";
    private final String APPUID = "05bd7806-3026-11e5-8396-c86000a05d5f";

    @Resource
    private UserService userService;
    @Resource
    private AuthorityService authorityService;

    public WebUser CurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() == false && subject.isAuthenticated() == false) {
            return null;
        }

        WebUser webUser = (WebUser) subject.getSession().getAttribute(WEBUSER_KEY);
        if (webUser == null) {
            webUser = initWebUser();
            subject.getSession().setAttribute(WEBUSER_KEY, webUser);
        }
        return webUser;
    }

    public List<Menu> GetUserMenuTree() {

        List<Menu> list = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() == false && subject.isAuthenticated() == false) {
            return list;
        }

        list = (List<Menu>) subject.getSession().getAttribute(USERMENUS_KEY);

        if (list == null) {

            List<model.Resource> list_resources = this.authorityService.GetUserMenu(SecurityUtils.getSubject().getPrincipal().toString(), APPUID);

            list = this.GenMenuTree("00000000-0000-0000-0000-000000000000", list_resources);

            Collections.sort(list);

            subject.getSession().setAttribute(USERMENUS_KEY, list);
        }
        return list;
    }

    public List<Menu> GetUserMenus() {

        List<Menu> list = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() == false && subject.isAuthenticated() == false) {
            return list;
        }
        List<model.Resource> list_resources = this.authorityService.GetUserMenu(SecurityUtils.getSubject().getPrincipal().toString(), APPUID);

        for (model.Resource resource : list_resources) {
            Menu menu = new Menu(resource.getUid(), resource.getName(), resource.getState(), resource.getIconClass(), resource.getUrl(), resource.getPriority());
            list.add(menu);
        }

        Collections.sort(list);
        return list;
    }

    public List<Permission> GetUserPermission() {

        List<Permission> list = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() == false && subject.isAuthenticated() == false) {
            return list;
        }

        List<model.Resource> list_resources = this.authorityService.GetUserPermissions(SecurityUtils.getSubject().getPrincipal().toString(), APPUID);
        list = new ArrayList<>(list_resources.size());
        for (model.Resource resource : list_resources) {
            Permission permission = new Permission(resource.getUid(), resource.getName(), resource.getAuthorityCode(), resource.getParentUid());
            list.add(permission);
        }
        return list;
    }


    public Set<String> GetUserPermissions() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() == false && subject.isAuthenticated() == false) {
            return null;
        }

        List<model.Resource> resourceList = this.authorityService.GetUserPermissions(SecurityUtils.getSubject().getPrincipal().toString(), APPUID);
        Set<String> permission = new HashSet<String>(resourceList.size());
        for (model.Resource resource : resourceList) {
            permission.add(resource.getAuthorityCode());
        }

        return permission;
    }

    public Set<String> GetUserRoles() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() == false && subject.isAuthenticated() == false) {
            return null;
        }

        List<Role> roles = this.authorityService.GetUserRoles(SecurityUtils.getSubject().getPrincipal().toString());
        Set<String> roleStr = new HashSet<>(roles.size());

        for (Role role : roles) {
            roleStr.add(role.getName());
        }

        return roleStr;
    }


    private List<Menu> GenMenuTree(String parentuid, List<model.Resource> list_resources) {

        List<Menu> list_menu = new ArrayList<>();

        for (model.Resource resource : list_resources) {

            if (resource.getParentUid().equalsIgnoreCase(parentuid)) {
                Menu menu = new Menu(resource.getUid(), resource.getName(), resource.getState(), resource.getIconClass(), resource.getUrl(), resource.getPriority());
                menu.setChildren(this.GenMenuTree(menu.getUid(), list_resources));
                Collections.sort(menu.getChildren());
                list_menu.add(menu);
            }
        }
        return list_menu;
    }


    private WebUser initWebUser() {
        User u = userService.InitWebUserByName(SecurityUtils.getSubject().getPrincipal().toString());

        WebUser webUser = new WebUser();
        webUser.setUid(u.getUid());
        webUser.setUsername(u.getUsername());
        webUser.setEmail(u.getEmail());
        webUser.setEmployeeName(u.getUsername());
        webUser.setIsadmin(u.isIsadmin());

        webUser.setPostName("集团中心开发");
        webUser.setLastLogin(u.getLastlogin());
        webUser.setAvatarUrl("/resources/image/avatars/DefaultAvatar.jpg");

//        webUser.setModules(InitModule());
        return webUser;
    }

//    private static List<Module> InitModule() {
//        List<Module> list = new ArrayList<Module>(3);
//
//        Module m = new Module(UUID.randomUUID().toString(), "机构设置", "org", " fa-list");
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "城市管理", "city", "/views/org/city/city.js"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "机构管理", "organization", "/views/org/organization/organization.js", "fa-list-ol"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "部门设置", "dept", "/views/org/dept/dept.js"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "职位设置", "post", "/views/org/post/post.js"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "地区设置", "region", "/views/org/user.js"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "人员管理", "employee", "/views/org/user/user.js"));
//        list.add(m);
//
//        m = new Module(UUID.randomUUID().toString(), "权限设置", "auth", "fa-wrench");
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "用户管理", "user", "/views/auth/user/user.js", "fa-user"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "角色管理", "role", "/views/auth/role/role.js"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "系统参数", "sysparams", "/views/org/user.js"));
//        list.add(m);
//
//        m = new Module(UUID.randomUUID().toString(), "基础设置", "application", "fa-list");
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "应用程序类型", "type", "/views/application/type/applicationType.js", "fa-list-ol"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "应用模块", "app", "/views/application/app/application.js"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "功能模块", "resource", "/views/application/resource/resource.js"));
//        list.add(m);
//
//        m = new Module(UUID.randomUUID().toString(), "其它设置", "other");
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "其它机构", "otherOrg", "/views/org/user.js"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "其它部门", "otherDept", "/views/org/dept.js"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "其它职位", "otherPost", "/views/org/user.js"));
//        m.getMenus().add(new model.Menu(UUID.randomUUID().toString(), "其它地区", "otherRegion", "/views/org/user.js"));
//        list.add(m);
//
//        return list;
//    }
}
