package shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;

import util.JsonResponseMsg;
import util.JsonUtil;

public class AuthcFilter extends AuthenticationFilter
{

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
	{

		// 没有登录，重定向到登录页面
		saveRequest(request);
		ShiroHttpServletRequest req = (ShiroHttpServletRequest) request;
		System.out.println("AuthcFilter:" + req.getRequestURL().toString());

		boolean ajax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
		String ajaxFlag = null == request.getParameter("ajax") ? "false" : request.getParameter("ajax");
		boolean isAjax = ajax || ajaxFlag.equalsIgnoreCase("true");

		// WebUtils.issueRedirect(request, response, this.getLoginUrl());
		//
		if (isAjax)
		{
			HttpServletResponse res = WebUtils.toHttp(response);
			res.setContentType("text/html");
			res.setCharacterEncoding("utf-8");

			JsonResponseMsg jsonResponseMsg = new JsonResponseMsg();
			jsonResponseMsg.setAuthenicated(false);
			jsonResponseMsg.setResponseMsg("当前访问需要授权(60002)，请联系管理员。");
			jsonResponseMsg.setHttpStatus(401);
			res.getWriter().write(JsonUtil.ToJson(jsonResponseMsg));
			// res.sendError(401);这里不返回错误状态，因为这里只是业务逻辑的错误，并非服务器配置上的错误
			res.getWriter().flush();

		}
		else
		{
			WebUtils.issueRedirect(request, response, this.getLoginUrl());
		}
		return false;
	}

}
