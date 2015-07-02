package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Module implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4971129146565075462L;
	private String uid;
	private String name;
	private String state;

	private List<Menu> menus;

	public Module(String uid, String name, String state)
	{
		this.uid = uid;
		this.name = name;
		this.state = state;
		menus = new ArrayList<Menu>();
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

	public List<Menu> getMenus()
	{
		return menus;
	}

	public void setMenus(List<Menu> menus)
	{
		this.menus = menus;
	}

}
