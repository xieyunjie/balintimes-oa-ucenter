package model;

import java.io.Serializable;
import java.util.Date;

public class Application implements Serializable {

	private static final long serialVersionUID = -1411141711813542173L;
	
	private String uid;
	private String name;
	private String typeUid;
	private String typeName;
	private String iconUrl;
	private String url;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeUid() {
		return typeUid;
	}
	public void setTypeUid(String typeUid) {
		this.typeUid = typeUid;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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

}
