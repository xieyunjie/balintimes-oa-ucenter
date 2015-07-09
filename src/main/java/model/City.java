package model;

import java.io.Serializable;

/**
 * Created by AlexXie on 2015/7/13.
 */
public class City implements Serializable {


    private String uid;
    private  String name;


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
