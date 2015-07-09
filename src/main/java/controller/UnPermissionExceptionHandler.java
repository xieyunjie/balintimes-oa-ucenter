package controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import util.JsonResponseMsg;
import util.JsonUtil;

/**
 * Created by AlexXie on 2015/7/27.
 */
@ControllerAdvice
public class UnPermissionExceptionHandler {

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
        ShiroHttpServletResponse response = (ShiroHttpServletResponse) request.getNativeResponse();
        ShiroHttpServletRequest req = (ShiroHttpServletRequest) request.getNativeRequest();

        try {
            boolean ajax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
            String ajaxFlag = null == request.getParameter("ajax") ? "false" : request.getParameter("ajax");
            boolean isAjax = ajax || ajaxFlag.equalsIgnoreCase("true");

            if (isAjax) {

                JsonResponseMsg jsonResponseMsg = new JsonResponseMsg();
                jsonResponseMsg.setPermission(false);
                jsonResponseMsg.setResponseMsg("当前访问需要授权(40001)，请联系管理员。");

                return JsonUtil.ToJson(jsonResponseMsg);

            } else {
                WebUtils.issueRedirect(req, response, "/login");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";

    }
}
