package dao.impl;

import dao.OrganizationDao;
import mappers.OrganizationMapper;
import model.Organization;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AlexXie on 2015/7/13.
 */

@Repository("organizationDao")
public class OrganizationDaoImpl implements OrganizationDao {
    @Resource
    private OrganizationMapper organizationMapper;

    public List<Organization> GetOrgList() {
        return this.organizationMapper.GetOrgList();
    }

    public List<Organization> GetOrgSet(String orgname) {
        return this.organizationMapper.GetOrgSet(orgname);
    }


    public Organization GetOneOrg(String uid) {
        return this.organizationMapper.GetOneOrg(uid);
    }

    public void CreateOrg(Organization organization) {
        this.organizationMapper.CreateOrg(organization);
    }

    public void UpdateOrg(Organization organization) {
        this.organizationMapper.UpdateOrg(organization);
    }

    public void UpdateOrgDel(String uid, String employeename) {
        Map<String, Object> paramsMap = new HashMap<String, Object>(3);
        paramsMap.put("uid", uid);
        paramsMap.put("editby", employeename);

        this.organizationMapper.UpdateOrgDel(paramsMap);
    }

    public void TransferParent(String uid, String parentUID, String employeename) {
        Map<String, Object> paramsMap = new HashMap<String, Object>(3);
        paramsMap.put("uid", uid);
        paramsMap.put("parentuid", parentUID);
        paramsMap.put("editby", employeename);

        this.organizationMapper.TransferParent(paramsMap);
    }
}
