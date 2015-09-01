package util;

import model.Role;
import model.authority.Employee;
import model.authority.Menu;
import model.authority.Permission;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import service.AuthorityService;
import shiro.WebUser;

import javax.annotation.Resource;
import java.util.*;

@Component
public class WebUserUtil {
    private final String WEBUSER_KEY = "WEBUSER";
    private final String USERMENUS_KEY = "USERMENUS";
    private final String APPUID = "05bd7806-3026-11e5-8396-c86000a05d5f";

    @Resource
    private AuthorityService authorityService;

    public WebUser InitUser(String username) {
        WebUser webUser = initWebUser(username);
        return webUser;
    }

    public WebUser CurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() == false && subject.isAuthenticated() == false) {
            return null;
        }

        WebUser webUser = (WebUser) subject.getSession().getAttribute(WEBUSER_KEY);
        if (webUser == null) {
            webUser = initWebUser(SecurityUtils.getSubject().getPrincipal().toString());
            subject.getSession().setAttribute(WEBUSER_KEY, webUser);
        }
        return webUser;
    }

    public List<Menu> GetUserMenuTree(String username) {

        List<Menu> list = new ArrayList<>();

        List<model.Resource> list_resources = this.authorityService.GetUserMenu(username, APPUID);

        list = this.GenMenuTree("00000000-0000-0000-0000-000000000000", list_resources);

        Collections.sort(list);

        return list;
    }

    public List<Menu> GetUserMenuTree() {
        List<Menu> list = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() == false && subject.isAuthenticated() == false) {
            return list;
        }

        list = (List<Menu>) subject.getSession().getAttribute(USERMENUS_KEY);

        if (list == null) {
            list = this.GetUserMenuTree(SecurityUtils.getSubject().getPrincipal().toString());

            subject.getSession().setAttribute(USERMENUS_KEY, list);
        }
        return list;

    }

    public List<Menu> GetUserMenus(String username) {
        List<Menu> list = new ArrayList<>();
        List<model.Resource> list_resources = this.authorityService.GetUserMenu(username, APPUID);

        for (model.Resource resource : list_resources) {
            Menu menu = new Menu(resource.getUid(), resource.getName(), resource.getState(), resource.getIconClass(), resource.getUrl(), resource.getPriority());
            list.add(menu);
        }

        Collections.sort(list);
        return list;
    }

    public List<Menu> GetUserMenus() {
        List<Menu> list = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() == false && subject.isAuthenticated() == false) {
            return list;
        }

        return GetUserMenus(SecurityUtils.getSubject().getPrincipal().toString());
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

    private WebUser initWebUser(String username) {
        Employee employee = authorityService.GetEmployee(username);

        WebUser webUser = new WebUser();
        webUser.setUid(employee.getUid());
        webUser.setEmployeeName(employee.getEmployeename());
        webUser.setUsername(employee.getUsername());
        webUser.setEmail(employee.getUsername());
        webUser.setUsername(employee.getUsername());
        webUser.setAdmin(employee.getIsadmin());
        webUser.setEmail(employee.getEmail());
        webUser.setUsertypename(employee.getUsertypename());
        webUser.setLastLogin(employee.getLastlogin());
        webUser.setAvatarUrl(employee.getAvatarurl());
        webUser.setPostList(employee.getPosts());

        return webUser;
    }
}

