package model;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {

	private static final long serialVersionUID = -4935408337811905947L;

	private String uid;
	private String name;
	private String parentUid;
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

	public String getParentUid() {
		return parentUid;
	}

	public void setParentUid(String parentUid) {
		this.parentUid = parentUid;
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
