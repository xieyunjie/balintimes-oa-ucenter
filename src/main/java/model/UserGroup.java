package model;

import java.io.Serializable;

public class UserGroup implements Serializable {

	private static final long serialVersionUID = -7550715239549912247L;

	private String uid;
	private String name;

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

}
