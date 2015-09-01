package shiro;

import model.authority.EmployeePost;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WebUser implements Serializable {
    private static final long serialVersionUID = -5311350500778209368L;

    private String uid;
    private String username;
    private boolean admin;
    private String employeeName;
    private String usertypename;
    private String email;

    private Date lastLogin;
    private String avatarUrl;

    private List<EmployeePost> postList;


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


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<EmployeePost> getPostList() {
        return postList;
    }

    public void setPostList(List<EmployeePost> postList) {
        this.postList = postList;
    }

    public String getUsertypename() {
        return usertypename;
    }

    public void setUsertypename(String usertypename) {
        this.usertypename = usertypename;
    }
}
