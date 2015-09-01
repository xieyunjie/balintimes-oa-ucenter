package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupRoleType {
	private String groupuid;
	private int[] roletypes;

	@JsonCreator
	public GroupRoleType(@JsonProperty("groupid") String groupid,
			@JsonProperty("roletypes") int[] roletypes) {
		this.groupuid = groupid;
		this.roletypes = roletypes;
	}

	public String getGroupuid() {
		return groupuid;
	}

	public void setGroupuid(String groupuid) {
		this.groupuid = groupuid;
	}

	public int[] getRoletypes() {
		return roletypes;
	}

	public void setRoletypes(int[] roletypes) {
		this.roletypes = roletypes;
	}

}
