package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexXie on 2015/7/13.
 */
public class OrganizationTree extends Organization {
    private List<OrganizationTree> children;

    public OrganizationTree(Organization organization) {
        this.setUid(organization.getUid());
        this.setParentuid(organization.getParentuid());
        this.setName(organization.getName());
        this.setStatus(organization.getStatus());
        this.setCityuid(organization.getCityuid());
        this.setCityname(organization.getCityname());
        this.setTypeflag(organization.isTypeflag());
        this.setDelflag(organization.isDelflag());
        this.setComment(organization.getComment());
        this.setCreateby(organization.getCreateby());
        this.setCreatetime(organization.getCreatetime());
        this.setEditby(organization.getEditby());
        this.setEdittime(organization.getEdittime());

        this.setChildren(new ArrayList<OrganizationTree>());

    }

    public List<OrganizationTree> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationTree> children) {
        this.children = children;
    }
}
