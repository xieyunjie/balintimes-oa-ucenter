package controller;

import base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.WebUser;
import util.JsonUtil;
import util.WebUserUtil;

import javax.annotation.Resource;

@Controller
@RequestMapping("home")
public class HomeController extends BaseController {

    @Resource
    private WebUserUtil WebUserUtil;

    @RequestMapping("")
    public String toHome() {
        return "home";
    }

    @RequestMapping("inituser")
    @ResponseBody
    public String InitUser() {
        WebUser webUser = WebUserUtil.CurrentUser();

        return JsonUtil.ToJson(webUser);
    }

//    @RequestMapping("usermodule")
//    @ResponseBody
//    public String UserModule() {
//
//        return JsonUtil.ToJson(WebUserUtil.CurrentUser().getModules());
//    }

    @RequestMapping("usermenus")
    @ResponseBody
    public String GetUserMenus() {

        return JsonUtil.ToJson(WebUserUtil.GetUserMenus());
    }

    @RequestMapping("userpermissions")
    @ResponseBody
    public String GetUserPermissions() {

        return JsonUtil.ToJson(WebUserUtil.GetUserPermission());
    }


    // 暂时没什么用途，再想想。。
    @RequestMapping("userpermission")
    @ResponseBody
    public String CheckUserPermission(String src) {

        if (src.equalsIgnoreCase("/auth/admin/privacy.js") == true) {

            return JsonUtil.ResponseFailureMessage("无授权");
        }
        return JsonUtil.ResponseSuccessfulMessage();
    }
}
