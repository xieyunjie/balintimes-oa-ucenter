package webservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.JsonUtil;
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
