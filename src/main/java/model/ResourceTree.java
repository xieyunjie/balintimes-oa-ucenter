package model;

import java.util.List;

public class ResourceTree extends Resource {

	private static final long serialVersionUID = 7871544500586673532L;

	private int treeType;
	private List<ResourceTree> children;
	private boolean checked;
	private List<Resource> functions;

	public List<Resource> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Resource> functions) {
		this.functions = functions;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getTreeType() {
		return treeType;
	}

	public void setTreeType(int treeType) {
		this.treeType = treeType;
	}

	public List<ResourceTree> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceTree> children) {
		this.children = children;
	}
}
