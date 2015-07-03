package shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;

import util.JsonResponseMsg;
import util.JsonUtil;

public class UserFilter extends AccessControlFilter
{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception
	{
		if (isLoginRequest(request, response))
		{
			return true;
		}
		else
		{
			Subject subject = getSubject(request, response);
			// If principal is not null, then the user is known and should be
			// allowed access.
			return subject.getPrincipal() != null;
		}
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
	{
		// 没有登录，重定向到登录页面
		saveRequest(request);
		ShiroHttpServletRequest req = (ShiroHttpServletRequest) request;

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
			jsonResponseMsg.setResponseMsg("当前访问需要授权(50001)，请联系管理员。");
			jsonResponseMsg.setHttpStatus(401);
			res.getWriter().write(JsonUtil.ToJson(jsonResponseMsg));
//			res.sendError(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED);
			res.getWriter().flush();

		}
		else
		{
			WebUtils.issueRedirect(request, response, this.getLoginUrl());
		}
		return false;
	}

}
