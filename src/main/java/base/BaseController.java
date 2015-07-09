package base;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import util.LocalResourceMsg;
import util.WebUserUtil;

@Component
public class BaseController
{

	@Resource
	private HttpServletRequest request;

	@Resource
	protected WebUserUtil webUsrUtil;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	protected String LocalMsg(String code)
	{
		return LocalResourceMsg.GetMessage(request, code);
	}

}
