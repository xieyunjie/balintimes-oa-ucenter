package model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable
{
	private static final long serialVersionUID = -8567418333741144420L;

	private String uid;
	private String username;
	private String password;
	private Date createtime;

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

	public Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}
}
