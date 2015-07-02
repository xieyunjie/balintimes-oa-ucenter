package util;

import java.io.Serializable;

public class JsonResponseMsg implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6353181786593828249L;

	private String success = "true";
	private int httpStatus = 200;
	private String responseMsg = "";
	private boolean isAuthenicated = true;
	private boolean permission = true;

	private Object data;

	private int total;
	private int pageSize;

	public String getSuccess()
	{
		return success;
	}

	public void setSuccess(String success)
	{
		this.success = success;
	}

	public int getHttpStatus()
	{
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus)
	{
		this.httpStatus = httpStatus;
	}

	public String getResponseMsg()
	{
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg)
	{
		this.responseMsg = responseMsg;
	}

	public boolean isAuthenicated()
	{
		return isAuthenicated;
	}

	// 是否已经认证
	public void setAuthenicated(boolean isAuthenicated)
	{
		this.isAuthenicated = isAuthenicated;
	}

	public boolean isPermission()
	{
		return permission;
	}

	// 是否有权限
	public void setPermission(boolean permission)
	{
		this.permission = permission;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

}
