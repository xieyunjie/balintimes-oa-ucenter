package model.authority;

import java.io.Serializable;

/**
 * Created by AlexXie on 2015/8/3.
 */
public class Permission implements Serializable {

    private String uid;
    private String name;
    private String permission;
    private String parentuid;

    public Permission(String uid, String name, String permission, String parentuid) {
        this.uid = uid;
        this.name = name;
        this.permission = permission;
        this.parentuid = parentuid;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String getParentuid() {
        return parentuid;
    }
}
