package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Application;
import model.Resource;
import model.ResourceTree;
import model.RoleResource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.ApplicationService;
import service.ResourceService;
import service.RoleResourceService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("resource")
public class ResourceController extends BaseController {

	@javax.annotation.Resource
	private ResourceService resourceService;
	@javax.annotation.Resource
	private ApplicationService applicationService;
	@javax.annotation.Resource
	private RoleResourceService roleResourceService;

	@RequestMapping("list/{appUid}")
	@ResponseBody
	public String GetResourceTreeList(@PathVariable String appUid) {
		Application app = this.applicationService.GetApplication(appUid);
		List<Resource> resources = this.resourceService.GetResourceList(appUid);

		List<ResourceTree> list = new ArrayList<ResourceTree>();

		ResourceTree tree = new ResourceTree();
		tree.setUid(app.getUid());
		tree.setAppUid(app.getUid());
		tree.setAppName(app.getName());
		tree.setName(app.getName());
		tree.setTreeType(-1);
		tree.setResourceType(-1);
		tree.setForbidden(app.isForbidden());
		tree.setShowInMenu(app.isShowInMenu());
		tree.setChildren(new ArrayList<ResourceTree>());
		tree.setFunctions(new ArrayList<Resource>());
		tree.setState("");

		this.SetTreeNode(tree, resources,
				"00000000-0000-0000-0000-000000000000");

		list.add(tree);

		return JsonUtil.ResponseSuccessfulMessage(list);
	}

	private void SetTreeNode(ResourceTree tree, List<Resource> list,
			String parentUid) {
		if (tree == null)
			return;

		for (Resource item : list) {
			if (item.getParentUid().equals(parentUid)) {

				if (item.getResourceType() == 2)
					continue;

				ResourceTree node = new ResourceTree();
				node.setUid(item.getUid());
				node.setAppUid(item.getAppUid());
				node.setAppName(item.getAppName());
				node.setAuthorityCode(item.getAuthorityCode());
				node.setComment(item.getComment());
				node.setCreateBy(item.getCreateBy());
				node.setCreateTime(item.getCreateTime());
				node.setEditBy(item.getEditBy());
				node.setEditTime(item.getEditTime());
				node.setForbidden(item.isForbidden());
				node.setIconClass(item.getIconClass());
				node.setName(item.getName());
				node.setParentUid(item.getParentUid());
				node.setPriority(item.getPriority());
				node.setResourceType(item.getResourceType());
				node.setShowInMenu(item.isShowInMenu());
				node.setUrl(item.getUrl());
				node.setTreeType(item.getResourceType());
				node.setChildren(new ArrayList<ResourceTree>());
				node.setFunctions(new ArrayList<Resource>());
				node.setState(item.getState());

				List<Resource> rs = this.SetFunction(list, item.getUid());
				node.setFunctions(rs);

				this.SetTreeNode(node, list, node.getUid());

				List<ResourceTree> cs = tree.getChildren();
				cs.add(node);
				tree.setChildren(cs);
			}
		}

	}

	private List<Resource> SetFunction(List<Resource> list, String parentUid) {
		List<Resource> children = new ArrayList<Resource>();
		for (Resource item : list) {
			if (item.getResourceType() == 2
					&& item.getParentUid().equals(parentUid)) {
				children.add(item);
			}
		}
		return children;
	}

	@RequestMapping("listBytreebox/{appUid}/{istree}")
	@ResponseBody
	public String GetResourceTreeListByTreeBox(@PathVariable String appUid,
			@PathVariable int istree) {
		Application app = this.applicationService.GetApplication(appUid);
		List<Resource> resources = this.resourceService.GetResourceList(appUid);

		List<ResourceTree> list = new ArrayList<ResourceTree>();

		ResourceTree tree = new ResourceTree();
		// tree.setUid(app.getUid());
		tree.setUid("00000000-0000-0000-0000-000000000000");
		tree.setAppUid(app.getUid());
		tree.setAppName(app.getName());
		tree.setName(app.getName());
		tree.setTreeType(-1);
		tree.setResourceType(-1);
		tree.setForbidden(app.isForbidden());
		tree.setShowInMenu(app.isShowInMenu());
		tree.setParentUid("00000000-0000-0000-0000-000000000000");
		tree.setChildren(new ArrayList<ResourceTree>());

		this.SetTreeNodeByTreeBox(tree, resources,
				"00000000-0000-0000-0000-000000000000", istree);

		list.add(tree);

		return JsonUtil.ResponseSuccessfulMessage(list);
	}

	private void SetTreeNodeByTreeBox(ResourceTree tree, List<Resource> list,
			String parentUid, int istree) {
		if (tree == null)
			return;

		for (Resource item : list) {
			if (item.getParentUid().equals(parentUid)) {

				if (istree == 1 && item.getResourceType() == 2) {
					continue;
				}

				ResourceTree node = new ResourceTree();
				node.setUid(item.getUid());
				node.setAppUid(item.getAppUid());
				node.setAppName(item.getAppName());
				node.setAuthorityCode(item.getAuthorityCode());
				node.setComment(item.getComment());
				node.setCreateBy(item.getCreateBy());
				node.setCreateTime(item.getCreateTime());
				node.setEditBy(item.getEditBy());
				node.setEditTime(item.getEditTime());
				node.setForbidden(item.isForbidden());
				node.setIconClass(item.getIconClass());
				node.setName(item.getName());
				node.setParentUid(item.getParentUid());
				node.setPriority(item.getPriority());
				node.setResourceType(item.getResourceType());
				node.setShowInMenu(item.isShowInMenu());
				node.setUrl(item.getUrl());
				node.setTreeType(item.getResourceType());
				node.setChildren(new ArrayList<ResourceTree>());

				this.SetTreeNodeByTreeBox(node, list, node.getUid(), istree);

				List<ResourceTree> cs = tree.getChildren();
				cs.add(node);
				tree.setChildren(cs);
			}
		}

	}

	private void SetTreeNodeByRole(ResourceTree tree, List<Resource> list,
			String parentUid, List<RoleResource> roleResourceList) {

		if (tree == null)
			return;

		for (Resource item : list) {
			if (item.getParentUid().equals(parentUid)) {

				ResourceTree node = new ResourceTree();
				node.setUid(item.getUid());
				node.setAppUid(item.getAppUid());
				node.setAppName(item.getAppName());
				node.setAuthorityCode(item.getAuthorityCode());
				node.setComment(item.getComment());
				node.setCreateBy(item.getCreateBy());
				node.setCreateTime(item.getCreateTime());
				node.setEditBy(item.getEditBy());
				node.setEditTime(item.getEditTime());
				node.setForbidden(item.isForbidden());
				node.setIconClass(item.getIconClass());
				node.setName(item.getName());
				node.setParentUid(item.getParentUid());
				node.setPriority(item.getPriority());
				node.setResourceType(item.getResourceType());
				node.setShowInMenu(item.isShowInMenu());
				node.setUrl(item.getUrl());
				node.setTreeType(item.getResourceType());
				node.setChildren(new ArrayList<ResourceTree>());

				for (RoleResource it : roleResourceList) {
					if (item.getUid().equals(it.getResourcesUid())) {
						node.setChecked(true);
					}
				}

				this.SetTreeNodeByRole(node, list, node.getUid(),
						roleResourceList);

				List<ResourceTree> cs = tree.getChildren();
				cs.add(node);
				tree.setChildren(cs);
			}
		}
	}

	@RequestMapping("listByRole/{roleuid}/{appuid}")
	@ResponseBody
	public String GetResourceTreeListByRoleAndApp(@PathVariable String roleuid,
			@PathVariable String appuid) {

		List<Application> appList = new ArrayList<Application>();
		List<ResourceTree> list = new ArrayList<ResourceTree>();
		List<RoleResource> roleResourceList = this.roleResourceService
				.GetRoleResourceListByRole(roleuid);

		if (appuid.equals("0")) {
			appList = this.applicationService
					.GetApplicationListByNotForbidden();
		} else {
			Application app = this.applicationService.GetApplication(appuid);
			if (app != null)
				appList.add(app);
		}

		for (Application app : appList) {

			List<Resource> resources = this.resourceService
					.GetResourceListByNotForbidden(app.getUid());

			ResourceTree tree = new ResourceTree();
			tree.setUid(app.getUid());
			tree.setAppUid(app.getUid());
			tree.setAppName(app.getName());
			tree.setName(app.getName());
			tree.setTreeType(-1);
			tree.setResourceType(-1);
			tree.setForbidden(app.isForbidden());
			tree.setShowInMenu(app.isShowInMenu());
			tree.setChildren(new ArrayList<ResourceTree>());
			tree.setParentUid("00000000-0000-0000-0000-000000000000");

			this.SetTreeNodeByRole(tree, resources,
					"00000000-0000-0000-0000-000000000000", roleResourceList);

			list.add(tree);
		}

		return JsonUtil.ResponseSuccessfulMessage(list);
	}

	@RequestMapping("getResource/{uid}")
	@ResponseBody
	public String GetResource(@PathVariable String uid) {
		Resource resource = this.resourceService.GetResource(uid);
		return JsonUtil.ResponseSuccessfulMessage(resource);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String CreateResourceInfo(Resource resource) {
		resource.setUid(UUID.randomUUID().toString());
		resource.setCreateBy(this.webUsrUtil.CurrentUser().getEmployeeName());
		try {
			this.resourceService.CreateResourceInfo(resource);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return JsonUtil.ResponseFailureMessage(e.getMessage());
		}
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateResourceInfo(Resource resource) {
		resource.setEditBy(this.webUsrUtil.CurrentUser().getEmployeeName());
		try {
			this.resourceService.UpdateResourceInfo(resource);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return JsonUtil.ResponseFailureMessage(e.getMessage());
		}
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String DeleteResourceInfo(String uid) {
		this.resourceService.DeleteResourceInfo(uid);
		return JsonUtil.ResponseSuccessfulMessage("保存成功");
	}

	@RequestMapping(value = "saveRoleResources", method = RequestMethod.POST)
	@ResponseBody
	public String SaveRoleResources(String roleUid, String appUid,
			String resources) {
		try {
			this.roleResourceService.SaveRoleResources(roleUid, appUid,
					resources.split(","));
			return JsonUtil.ResponseSuccessfulMessage("保存成功");
		} catch (Exception ex) {
			return JsonUtil.ResponseFailureMessage(ex.getMessage());
		}
	}

}
