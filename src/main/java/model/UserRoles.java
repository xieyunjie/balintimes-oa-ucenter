package model;

import java.io.Serializable;

public class UserRoles implements Serializable {

	private static final long serialVersionUID = -4089843927317340256L;

	private String uid;
	private String userUid;
	private String roleUid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getRoleUid() {
		return roleUid;
	}

	public void setRoleUid(String roleUid) {
		this.roleUid = roleUid;
	}

}
