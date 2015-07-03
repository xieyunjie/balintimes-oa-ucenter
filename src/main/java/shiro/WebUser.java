package shiro;

import java.io.Serializable;
import java.util.List;

import model.Module;

public class WebUser implements Serializable
{
	private static final long serialVersionUID = -5311350500778209368L;

	private String uid;
	private String username;
	private boolean isadmin;
	private List<String> rolelist;
	private List<String> authList;

	private List<Module> modules;

	private String employeeUID;
	private String employeeName;
	private String deptname;
	private String email;

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

	public boolean isIsadmin()
	{
		return isadmin;
	}

	public void setIsadmin(boolean isadmin)
	{
		this.isadmin = isadmin;
	}

	public List<String> getRolelist()
	{
		return rolelist;
	}

	public void setRolelist(List<String> rolelist)
	{
		this.rolelist = rolelist;
	}

	public List<String> getAuthList()
	{
		return authList;
	}

	public void setAuthList(List<String> authList)
	{
		this.authList = authList;
	}

	public List<Module> getModules()
	{
		return modules;
	}

	public void setModules(List<Module> modules)
	{
		this.modules = modules;
	}

	public String getEmployeeUID()
	{
		return employeeUID;
	}

	public void setEmployeeUID(String employeeUID)
	{
		this.employeeUID = employeeUID;
	}

	public String getEmployeeName()
	{
		return employeeName;
	}

	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}

	public String getDeptname()
	{
		return deptname;
	}

	public void setDeptname(String deptname)
	{
		this.deptname = deptname;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}
