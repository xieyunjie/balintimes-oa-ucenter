package model;

import java.io.Serializable;

public class UserGroupDetail implements Serializable {

	private static final long serialVersionUID = 2020171693526161192L;

	private String uid;
	private String userGroupUid;
	private String userUid;
	private int roleType;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserGroupUid() {
		return userGroupUid;
	}

	public void setUserGroupUid(String userGroupUid) {
		this.userGroupUid = userGroupUid;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

}
