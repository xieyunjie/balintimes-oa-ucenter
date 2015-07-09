package model.authority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexXie on 2015/8/3.
 */
public class Menu implements Serializable, Comparable<Menu> {

    private String uid;
    private String name;
    private String state;
    private Integer priority = 0;
    private String iconclass;
    private String url;

    public Menu(String uid, String name, String state, String iconclass, String url, Integer priority) {
        this.uid = uid;
        this.name = name;
        this.state = state;
        this.iconclass = iconclass;
        this.url = url;
        this.priority = priority;

        this.children = new ArrayList<>();
    }

    private List<Menu> children;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconclass() {
        return iconclass;
    }

    public void setIconclass(String iconclass) {
        this.iconclass = iconclass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public int compareTo(Menu menu) {
        return  this.getPriority().compareTo(menu.getPriority());
    }
}
