package model;

import java.io.Serializable;
import java.util.List;

public class UserGroupRoleTypeTree implements Serializable {

	private static final long serialVersionUID = 1710722481723341537L;

	private String uid;
	private String name;
	private boolean checked;
	private int id;
	private String userUid;
	private List<UserGroupRoleTypeTree> children;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public List<UserGroupRoleTypeTree> getChildren() {
		return children;
	}

	public void setChildren(List<UserGroupRoleTypeTree> children) {
		this.children = children;
	}

}
