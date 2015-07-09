package model;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {

	private String uid;
	private String parentuid;
	private String parentname;
	private String name;
	private String organizationuid;
	private String organizationname;
	private String comment;
	private String createby;
	private Date createtime;
	private String editby;
	private Date edittime;

	public Post() {
		//this.uid = "0";
	}

	public void setUid(String value) {
		this.uid = value;
	}

	public String getUid() {
		return this.uid;
	}

	public void setParentuid(String value) {
		this.parentuid = value;
	}

	public String getParentuid() {
		return this.parentuid;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setOrganizationuid(String value) {
		this.organizationuid = value;
	}

	public String getOrganizationuid() {
		return this.organizationuid;
	}
	
	public void setOrganizationname(String value) {
		this.organizationname =value;		
	}
	
	public String getOrganizationname(){
		return this.organizationname;
	}


	public void setComment(String value) {
		this.comment = value;
	}

	public String getComment() {
		return this.comment;
	}

	public void setCreateby(String value) {
		this.createby = value;
	}

	public String getCreateby() {
		return this.createby;
	}

	public void setCreatetime(Date value) {
		this.createtime = value;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setEditby(String value) {
		this.editby = value;
	}

	public String getEditby() {
		return this.editby;
	}

	public void setEdittime(Date value) {
		this.edittime = value;
	}

	public Date getEdittime() {
		return this.edittime;
	}

	public void setParentname(String value) {
		this.parentname = value;
	}

	public String getParentname() {
		return this.parentname;
	}
}
