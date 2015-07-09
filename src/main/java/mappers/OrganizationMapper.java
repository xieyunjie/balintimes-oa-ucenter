package mappers;

import model.Organization;

import java.util.List;
import java.util.Map;

/**
 * Created by AlexXie on 2015/7/13.
 */
public interface OrganizationMapper {

    List<Organization> GetOrgList();

    List<Organization> GetOrgSet(String orgname);

    Organization GetOneOrg(String uid);

    void CreateOrg(Organization organization);

    void UpdateOrg(Organization organization);

    void UpdateOrgDel(Map<String, Object> params);

    void TransferParent(Map<String, Object> params);

}
