package model;

import java.util.List;

public class UserTree extends User {
	private List<UserTree> 	children;
	
	public UserTree(User user)
	{
		this.setUid(user.getUid());
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setIsadmin(user.isIsadmin());
		this.setEmployeename(user.getEmployeename());
		this.setUsertype(user.getUsertype());
		this.setUsertypename(user.getUsertypename());
		this.setEmail(user.getEmail());
		this.setComment(user.getComment());
		this.setCreateby(user.getCreateby());
		this.setCreatetime(user.getCreatetime());
		this.setEditby(user.getEditby());
		this.setEdittime(user.getEdittime());
		this.setLastlogin(user.getLastlogin());
		this.setSex(user.getSex());
		this.setBirthday(user.getBirthday());
		this.setIdcard(user.getIdcard());
		this.setAddress(user.getAddress());
		this.setMobile(user.getMobile());
		this.setTelephone(user.getTelephone());
		this.setEmergencycontact(user.getEmergencycontact());
		this.setAvatarurl(user.getAvatarurl());
		this.setPostuid(user.getPostuid());
		this.setPostname(user.getPostname());
		this.setParentuid(user.getParentuid());
	}
	
	public void setChildren(List<UserTree> children) {
		this.children = children;
	}
	
	public List<UserTree> getChildren() {
		return children;
	}
}
