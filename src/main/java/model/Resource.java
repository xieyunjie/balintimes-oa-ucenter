package model;

import java.io.Serializable;
import java.util.Date;

public class Resource implements Serializable {

	private static final long serialVersionUID = -8230906242675537492L;
	
	private String uid;
	private String appUid;
	private String appName;
	private String parentUid;
	private String name;
	private int priority;
	private int resourceType;
	private String iconClass;
	private String url;
	private String authorityCode;
	private String state;
	private boolean showInMenu;
	private boolean forbidden;
	private String comment;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAppUid() {
		return appUid;
	}
	public void setAppUid(String appUid) {
		this.appUid = appUid;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getParentUid() {
		return parentUid;
	}
	public void setParentUid(String parentUid) {
		this.parentUid = parentUid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getResourceType() {
		return resourceType;
	}
	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}
	public String getIconClass() {
		return iconClass;
	}
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAuthorityCode() {
		return authorityCode;
	}
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	public boolean isShowInMenu() {
		return showInMenu;
	}
	public void setShowInMenu(boolean showInMenu) {
		this.showInMenu = showInMenu;
	}
	public boolean isForbidden() {
		return forbidden;
	}
	public void setForbidden(boolean forbidden) {
		this.forbidden = forbidden;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEditBy() {
		return editBy;
	}
	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
