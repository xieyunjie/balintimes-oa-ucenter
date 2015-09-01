package webservice;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;
import shiro.WebUser;
import util.JsonUtil;
import util.RedisUserUtil;
import util.WebUserUtil;

import javax.annotation.Resource;

/**
 * Created by AlexXie on 2015/8/19.
 */
@Controller
@RequestMapping("ws/authority")
public class AuthorityController {

    @Resource
    private WebUserUtil webUserUtil;
    @Resource
    private RedisUserUtil redisUserUtil;
    @Resource
    private UserService userService = null;
    @Resource
    private PasswordService passwordService;

    @RequestMapping("userauthentication")
    @ResponseBody
    public String UserAuthentication(String username, String password) {

        String dbpsw = userService.getUserPassword(username);
        if ("".equals(dbpsw) || dbpsw == null) {
            return JsonUtil.ResponseFailureMessage("password or user is invald");
        }
        String inputpsw = passwordService.encryptPassword(password);

        if (inputpsw.equals(dbpsw)) {
            WebUser webUser = webUserUtil.InitUser(username);
            if (webUser == null) {
                return JsonUtil.ResponseFailureMessage("init user failture!");
            }

            String webUserKey = redisUserUtil.SetRedisWebUser(JsonUtil.ToJson(webUser));

            return JsonUtil.ResponseSuccessfulMessage(webUserKey);
        }
        return JsonUtil.ResponseFailureMessage("password or user is invald");

    }

    @RequestMapping("menutree/{username}")
    @ResponseBody
    public String GetUserMenuTree(@PathVariable String username) {
        return JsonUtil.ToJson(webUserUtil.GetUserMenuTree(username));
    }

    @RequestMapping("menus/{username}")
    @ResponseBody
    public String GetUserMenus(@PathVariable String username) {
        return JsonUtil.ToJson(webUserUtil.GetUserMenus(username));
    }

}
