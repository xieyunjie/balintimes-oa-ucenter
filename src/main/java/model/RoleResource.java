package model;

import java.io.Serializable;

public class RoleResource implements Serializable {

	private static final long serialVersionUID = -5359711421930955762L;
	
	private String uid;
	private String roleUid;
	private String resourcesUid;
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
	public String getResourcesUid() {
		return resourcesUid;
	}
	public void setResourcesUid(String resourcesUid) {
		this.resourcesUid = resourcesUid;
	}
	
}
