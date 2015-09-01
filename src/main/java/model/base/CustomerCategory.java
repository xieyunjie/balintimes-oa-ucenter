package model.base;

import java.io.Serializable;

public class CustomerCategory implements Serializable {

	private static final long serialVersionUID = 6069872495406463255L;
	
	private String uid;
	private String name;
	private String code;
	private String comment;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
