package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by AlexXie on 2015/7/13.
 */
public class Organization implements Serializable {
    private String uid;
    private String parentuid;
    private String parentname;
    private String name;
    private String cityuid;
    private String cityname;
    private boolean typeflag;
    private int status;
    private boolean delflag;
    private String comment;
    private String createby;
    private Date createtime;
    private String editby;
    private Date edittime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getParentuid() {
        return parentuid;
    }

    public void setParentuid(String parentuid) {
        this.parentuid = parentuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityuid() {
        return cityuid;
    }

    public void setCityuid(String cityuid) {
        this.cityuid = cityuid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public boolean isTypeflag() {
        return typeflag;
    }

    public void setTypeflag(boolean typeflag) {
        this.typeflag = typeflag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isDelflag() {
        return delflag;
    }

    public void setDelflag(boolean delflag) {
        this.delflag = delflag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getEditby() {
        return editby;
    }

    public void setEditby(String editby) {
        this.editby = editby;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }
}
