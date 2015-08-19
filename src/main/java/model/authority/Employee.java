package model.authority;

import model.Post;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AlexXie on 2015/8/19.
 */
public class Employee implements Serializable {

    private String uid;
    private String username;
    private String isadmin;
    private String employeename;
    private String usertypename;
    private String email;
    private String lastlogin;
    private String sex;
    private String avatarurl;
    private int rootdeep = 0;

    private List<Post> posts;


}
