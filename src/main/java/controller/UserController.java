package controller;

import base.BaseController;
import model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;
import util.JsonUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;


    @RequestMapping("list")
    @ResponseBody
    public String UserList() throws InterruptedException {
        Thread.sleep(1 * 1000);

        // System.out.println(LocalMsg("name"));

        List<User> list = new ArrayList<User>();

        list = this.userService.GetUserList();

        return JsonUtil.ResponseSuccessfulMessage(list);
    }

    @RequestMapping("listbypage")
    @ResponseBody
    public String UserListforPage(String username, String employeename, String usertype, String isenable, int page, int pageSize) {
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isenable != null) {
            isenable = isenable.equals("-1") ? "" : isenable;
        }


        tuples.TuplePage<List<User>, Integer> result = this.userService.GetUserList(username, employeename, usertype, isenable, page, pageSize);

        return JsonUtil.ResponseSuccessfulMessage(result.objectList, result.objectTotalCount);
    }

    @RequestMapping("getuser/{uid}")
    @ResponseBody
    public String GetUser(@PathVariable String uid) {
        User user;
        if (uid.equals("0")) {
            user = new User();
        } else {
            user = this.userService.getUser(uid);
        }

        return JsonUtil.ResponseSuccessfulMessage(user);
    }

    @RequestMapping("getuser")
    @ResponseBody
    public String GetSomnUser() {
        User user;
        user = new User();

        return JsonUtil.ResponseSuccessfulMessage(user);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(User user) {
        // 相同用户名
        if (this.userService.ExistsUserName(user.getUsername()) == true) {
            return JsonUtil.ResponseFailureMessage("已经存在相同用户名，请重新录入。");
        }

        user.setUid(UUID.randomUUID().toString());
        user.setCreateby(webUsrUtil.CurrentUser().getEmployeeName());
        user.setCreatetime(new Date());

        this.userService.create(user);

        return JsonUtil.ResponseSuccessfulMessage("保存成功");
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public String updateUser(User user) {
        // 相同用户名
        if (this.userService.ExistsUserName(user.getUsername(), user.getUid()) == true) {
            return JsonUtil.ResponseFailureMessage("已经存在相同用户名，请重新录入。");
        }

        user.setEditby(webUsrUtil.CurrentUser().getEmployeeName());
        user.setEdittime(new Date());
        this.userService.updateUser(user);
        return JsonUtil.ResponseSuccessfulMessage("修改成功");
    }

    @RequestMapping(value = "resetpassword")
    @ResponseBody
    public String ResetPassword(String UID) {
        this.userService.UpdatePassword(UID, "1");

        return JsonUtil.ResponseSuccessfulMessage("重置成功");
    }

    @RequestMapping(value = "updatepassword", method = RequestMethod.POST)
    @ResponseBody
    public String UpdateUserPassword(String oldpassword, String newpassword) {

        try {
            String username = this.userService.UpdatePassword(webUsrUtil.CurrentUser().getUid(), oldpassword, newpassword);
            if (username.equals("")) {
                return JsonUtil.ResponseFailureMessage("修改密码错误！");
            }
            shiro.Utils.logout();
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, newpassword);
            subject.login(token);

            return JsonUtil.ResponseSuccessfulMessage("修改密码成功！");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.ResponseFailureMessage("修改密码异常!!" + e.getMessage());
        }

    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteUser(String UID) {
        this.userService.deleteUser(UID, webUsrUtil.CurrentUser().getEmployeeName());

        return JsonUtil.ResponseSuccessfulMessage("删除成功");
    }

    @RequestMapping("getnulluser")
    @ResponseBody
    public String GetNullUser() {
        return JsonUtil.ResponseSuccessfulMessage("获取了一个 null 的 user");
    }

    @RequestMapping("getresourcepermission")
    @ResponseBody
    public String GetResourcePermission(String state) {

        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (state.equals("auth/user")) {
            return JsonUtil.ResponsePermissionMessage(false, "无权限");
        } else {
            return JsonUtil.ResponsePermissionMessage(true, "");
        }

    }
}
