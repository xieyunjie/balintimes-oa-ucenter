package dao;

import model.Organization;

import java.util.List;

/**
 * Created by AlexXie on 2015/7/13.
 */
public interface OrganizationDao {

    List<Organization> GetOrgList();

    List<Organization> GetOrgSet(String orgname);

    Organization GetOneOrg(String uid);

    void CreateOrg(Organization organization);

    void UpdateOrg(Organization organization);

    void UpdateOrgDel(String uid, String employeename);

    void TransferParent(String uid, String parentUID, String employeename);


}
