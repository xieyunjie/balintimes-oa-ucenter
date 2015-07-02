package shiro;

import java.util.List;

import org.apache.shiro.authc.SimpleAuthenticationInfo;

public class WebAuthenticationInfo extends SimpleAuthenticationInfo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 210916612277258969L;

	private String deptname;
	private String email;
	private List<String> authList;

	public WebAuthenticationInfo(String username, String password, String name)
	{
		super(username, password, name);
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

	public List<String> getAuthList()
	{
		return authList;
	}

	public void setAuthList(List<String> authList)
	{
		this.authList = authList;
	}

}
