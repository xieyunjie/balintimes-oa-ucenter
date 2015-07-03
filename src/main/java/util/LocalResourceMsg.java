package util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContext;

public class LocalResourceMsg
{
	public static String GetMessage(HttpServletRequest request, String code)
	{
		RequestContext requestContext = new RequestContext(request);
		return requestContext.getMessage(code);
	}
}
