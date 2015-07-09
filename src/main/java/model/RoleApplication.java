package model;

import java.io.Serializable;

public class RoleApplication implements Serializable{

	private static final long serialVersionUID = 8833345121686394148L;
	
	private String uid;
	private String roleUid;
	private String appUid;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getRoleUid() {
		return roleUid;
	}
	public void setRoleUid(String roleUid) {
		this.roleUid = roleUid;
	}
	public String getAppUid() {
		return appUid;
	}
	public void setAppUid(String appUid) {
		this.appUid = appUid;
	}
	
}
