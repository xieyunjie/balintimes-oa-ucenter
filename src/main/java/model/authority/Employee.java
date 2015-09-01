package model.authority;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by AlexXie on 2015/8/19.
 */
public class Employee implements Serializable {

    private String uid;
    private String username;
    private boolean isadmin;
    private String employeename;
    private String usertypename;
    private String email;
    private Date lastlogin;
    private String sex;
    private String avatarurl;
    private int rootdeep = 0;

    private List<EmployeePost> posts;

    public Employee() {
        // TODO Auto-generated constructor stub
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(boolean isadmin) {
        this.isadmin = isadmin;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setUsertypename(String usertypename) {
        this.usertypename = usertypename;
    }

    public String getUsertypename() {
        return usertypename;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setRootdeep(int rootdeep) {
        this.rootdeep = rootdeep;
    }

    public int getRootdeep() {
        return rootdeep;
    }

    public void setPosts(List<EmployeePost> posts) {
        this.posts = posts;
    }

    public List<EmployeePost> getPosts() {
        return posts;
    }


    @Override
    public String toString() {
        return uid.toString() + "##" + employeename;
    }
}
