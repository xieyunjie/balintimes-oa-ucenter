package model;

import java.io.Serializable;

public class Menu implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6376524548884186051L;
	private String uid;
	private String name;
	private String state;
	private String url;

	public Menu(String uid, String name, String state, String url)
	{
		super();
		this.uid = uid;
		this.name = name;
		this.state = state;
		this.url = url;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

}
