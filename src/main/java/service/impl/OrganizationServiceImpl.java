package service.impl;

import annotation.CustomerTransactional;
import dao.OrganizationDao;
import model.Organization;
import org.springframework.stereotype.Service;
import service.OrganizationService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by AlexXie on 2015/7/13.
 */
@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

    @Resource
    private OrganizationDao organizationDao;

    public List<Organization> GetOrgList() {
        return this.organizationDao.GetOrgList();
    }

    public Organization GetOneOrg(String uid) {
        return this.organizationDao.GetOneOrg(uid);
    }

    @CustomerTransactional
    public void CreateOrg(Organization organization) {
        this.organizationDao.CreateOrg(organization);
    }

    @CustomerTransactional
    public void UpdateOrg(Organization organization) {
        this.organizationDao.UpdateOrg(organization);
    }

    @CustomerTransactional
    public void UpdateOrgDel(String uid, String employeename) {
        this.organizationDao.UpdateOrgDel(uid, employeename);
    }

    @CustomerTransactional
    public void TransferParent(String uid, String parentUID, String employeename) {
        this.organizationDao.TransferParent(uid, parentUID, employeename);
    }

    @CustomerTransactional
    public void UpdateOrgTest(String uid, String employee) {
        // a8edceff-afd8-4a39-bbf5-ceb0f2a4fb6e
        //3d7b5e0d-2938-11e5-970b-c86000a05d5f

        System.out.println("orgservices");
    }

    public List<Organization> GetOrgSet(String orgname) {
        return this.organizationDao.GetOrgSet(orgname);
    }
}
