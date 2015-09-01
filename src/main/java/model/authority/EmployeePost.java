package model.authority;

import java.io.Serializable;

/**
 * Created by AlexXie on 2015/8/20.
 */
public class EmployeePost implements Serializable {

    private String postuid;
    private String postname;
    private String organizationuid;
    private String organizationname;

    public EmployeePost() {
    }

    public String getPostuid() {
        return postuid;
    }

    public void setPostuid(String postuid) {
        this.postuid = postuid;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getOrganizationuid() {
        return organizationuid;
    }

    public void setOrganizationuid(String organizationuid) {
        this.organizationuid = organizationuid;
    }

    public String getOrganizationname() {
        return organizationname;
    }

    public void setOrganizationname(String organizationname) {
        this.organizationname = organizationname;
    }
}
