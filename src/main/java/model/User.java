package model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable
{
	private static final long serialVersionUID = -8567418333741144420L;

	private String uid;
	private String username;
	private String password;
	private boolean isadmin;
	private String employeename;
	private boolean isenable;
	private boolean delflag;
	private String usertype;
	private String usertypename;
	private String email;
	private String comment;
	private String createby;
	private Date createtime;
	private String editby;
	private Date edittime;
	private Date lastlogin;

	public User()
	{
		this.uid = "0";
		this.isenable = true;
		this.delflag = false;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isIsadmin()
	{
		return isadmin;
	}

	public void setIsadmin(boolean isadmin)
	{
		this.isadmin = isadmin;
	}

	public String getEmployeename()
	{
		return employeename;
	}

	public void setEmployeename(String employeename)
	{
		this.employeename = employeename;
	}

	public boolean isIsenable()
	{
		return isenable;
	}

	public void setIsenable(boolean isenable)
	{
		this.isenable = isenable;
	}

	public boolean isDelflag()
	{
		return delflag;
	}

	public void setDelflag(boolean delflag)
	{
		this.delflag = delflag;
	}

	public String getUsertype()
	{
		return usertype;
	}

	public void setUsertype(String usertype)
	{
		this.usertype = usertype;
	}

	public String getUsertypename()
	{
		return usertypename;
	}

	public void setUsertypename(String usertypename)
	{
		this.usertypename = usertypename;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getCreateby()
	{
		return createby;
	}

	public void setCreateby(String createby)
	{
		this.createby = createby;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}

	public String getEditby()
	{
		return editby;
	}

	public void setEditby(String editby)
	{
		this.editby = editby;
	}

	public Date getEdittime()
	{
		return edittime;
	}

	public void setEdittime(Date edittime)
	{
		this.edittime = edittime;
	}

	public Date getLastlogin()
	{
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin)
	{
		this.lastlogin = lastlogin;
	}

}
