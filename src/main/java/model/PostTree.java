package model;

import java.util.ArrayList;
import java.util.List;

public class PostTree extends Post {
	private List<PostTree> children;
	
	public PostTree(Post post)
	{
		this.setUid(post.getUid());
		this.setName(post.getName());
		this.setParentuid(post.getParentuid());
		//this.setParentName(post.getParentName());
		this.setOrganizationuid(post.getOrganizationuid());
		this.setOrganizationname(post.getOrganizationname());
		this.setComment(post.getComment());
		this.setCreateby(post.getCreateby());
		this.setCreatetime(post.getCreatetime());
		this.setEditby(post.getEditby());
		this.setEdittime(post.getEdittime());
		
		this.setChildren(new ArrayList<PostTree>());
	}
	
	public void setChildren(List<PostTree> value) {
		this.children=value;		
	}
	
	public List<PostTree> getChildren() {
		return children;
	}
}
