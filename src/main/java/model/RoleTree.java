package model;

import java.util.List;

public class RoleTree extends Role {

	private static final long serialVersionUID = 6704525827958421369L;

	private List<RoleTree> children;
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<RoleTree> getChildren() {
		return children;
	}

	public void setChildren(List<RoleTree> children) {
		this.children = children;
	}

}
