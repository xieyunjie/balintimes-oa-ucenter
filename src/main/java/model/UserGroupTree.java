package model;

import java.util.List;

public class UserGroupTree extends UserGroup {

	private static final long serialVersionUID = 2380443442590951370L;

	private int roleType;
	private String roleTypeName;
	private String userUid;

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

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	private List<UserGroupTree> children;
	private int treeType;

	public List<UserGroupTree> getChildren() {
		return children;
	}

	public void setChildren(List<UserGroupTree> children) {
		this.children = children;
	}

	public int getTreeType() {
		return treeType;
	}

	public void setTreeType(int treeType) {
		this.treeType = treeType;
	}

}
