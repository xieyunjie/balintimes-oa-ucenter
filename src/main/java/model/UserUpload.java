package model;

import java.io.File;
import java.io.Serializable;

public class UserUpload implements Serializable{
	private static final long serialVersionUID = -8567418333741144420L;
	private String uid;
	private String username;
	private File attachment;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public File getAttachment() {
		return attachment;
	}
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	
}
