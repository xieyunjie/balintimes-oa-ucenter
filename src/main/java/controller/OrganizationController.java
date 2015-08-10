package controller;

import base.BaseController;
import model.Organization;
import model.OrganizationTree;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.OrganizationService;
import util.JsonUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by AlexXie on 2015/7/13.
 */
@Controller
@RequestMapping("organization")
public class OrganizationController extends BaseController {

    @Resource
    private OrganizationService organizationService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public String GetOrgList() {

        List<Organization> list = this.organizationService.GetOrgList();

        return JsonUtil.ResponseSuccessfulMessage(list);
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @ResponseBody
    public String GetOrgTree() {

        List<Organization> list = this.organizationService.GetOrgList();

        List<OrganizationTree> trees = this.InitOrgTree(list);

        return JsonUtil.ResponseSuccessfulMessage(trees);
    }

    @RequestMapping(value = "tree/{orgname}", method = RequestMethod.GET)
    @ResponseBody
    public String GetOrgTreeSet(@PathVariable String orgname) {

        List<Organization> list = this.organizationService.GetOrgSet(orgname);

        List<OrganizationTree> trees = this.InitOrgTree(list);

        return JsonUtil.ResponseSuccessfulMessage(trees);
    }

    @RequestMapping(value = "/getone/{uid}")
    @ResponseBody
    public String GetOneOrg(@PathVariable String uid) {
        Organization organization = this.organizationService.GetOneOrg(uid);
        Organization parentOrg = this.organizationService.GetOneOrg(organization.getParentuid());
        if (parentOrg != null) {
            organization.setParentname(parentOrg.getName());
        }

        return JsonUtil.ResponseSuccessfulMessage(organization);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public String SaveOrg(Organization organization) {

        try {
            if (organization.getUid().equalsIgnoreCase("0")) {
                organization.setUid(UUID.randomUUID().toString());
                organization.setCreateby(webUsrUtil.CurrentUser().getEmployeeName());

                this.organizationService.CreateOrg(organization);
            } else {
                organization.setEditby(webUsrUtil.CurrentUser().getEmployeeName());
                this.organizationService.UpdateOrg(organization);
            }

            return JsonUtil.ResponseSuccessfulMessage("保存成功");
        } catch (Exception ex) {
            return JsonUtil.ResponseFailureMessage("保存失败！" + ex.getMessage());
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("organization:delete")
    public String DeleteOrg(String uid) {

        this.organizationService.UpdateOrgDel(uid, webUsrUtil.CurrentUser().getEmployeeName());

        return JsonUtil.ResponseSuccessfulMessage("删除成功");
    }

    @RequestMapping(value = "transfer", method = RequestMethod.POST)
    @ResponseBody
    public String Transfer(String uid, String parentuid) {

        this.organizationService.TransferParent(uid, parentuid, webUsrUtil.CurrentUser().getEmployeeName());

        return JsonUtil.ResponseSuccessfulMessage("删除成功");
    }

    private List<OrganizationTree> InitOrgTree(List<Organization> list) {
        List<OrganizationTree> trees = new ArrayList<OrganizationTree>();
        if (list == null) {
            return trees;
        }
        if (list.size() < 1) {
            return trees;
        }

        String rootUID = "00000000-0000-0000-0000-000000000001";
        OrganizationTree rootOrg = null;
        for (Organization organization : list) {
            if (organization.getUid().equalsIgnoreCase(rootUID)) {
                rootOrg = new OrganizationTree(organization);
                break;
            }
        }

        rootOrg.setChildren(this.GetChildren(list, rootOrg.getUid(), rootOrg.getName()));

        trees.add(rootOrg);

        return trees;
    }

    private List<OrganizationTree> GetChildren(List<Organization> list, String parentUID, String parentName) {

        List<OrganizationTree> tree = new ArrayList<OrganizationTree>();

        for (Organization organization : list) {
            if (organization.getParentuid().equalsIgnoreCase(parentUID)) {

                OrganizationTree node = new OrganizationTree(organization);
                node.setParentname(parentName);
                node.setChildren(this.GetChildren(list, organization.getUid(), node.getName()));
                tree.add(node);
            }

        }

        return tree;
    }
}
