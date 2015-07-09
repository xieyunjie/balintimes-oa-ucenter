package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import model.Post;
import model.PostTree;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.PostService;
import util.JsonUtil;
import base.BaseController;

@Controller
@RequestMapping("post")
public class PostController extends BaseController {

	@Resource
	public PostService postService;

	@RequestMapping("tree")
	@ResponseBody
	public String GetPostList() {
		List<Post> resultList = this.postService.GetPostList();
		List<PostTree> trees=this.InitOrgTree(resultList);	
		String testVar=JsonUtil.ResponseSuccessfulMessage(trees);
		System.out.println(JsonUtil.ResponseSuccessfulMessage(trees));
		return JsonUtil.ResponseSuccessfulMessage(trees);
	}
	
	private List<PostTree> InitOrgTree(List<Post> list) {
        List<PostTree> trees = new ArrayList<PostTree>();
        if (list == null) {
            return trees;
        }
        if (list.size() < 1) {
            return trees;
        }
                          
        String rootUID = "00000000-0000-0000-0000-000000000001";
        PostTree rootPost = null;
        for (Post Post : list) {
            if (Post.getUid().equalsIgnoreCase(rootUID)) {
            	rootPost = new PostTree(Post);
                break;
            }
        }

        rootPost.setChildren(this.GetChildren(list, rootPost.getUid(), rootPost.getName()));

        trees.add(rootPost);

        return trees;
    }

    private List<PostTree> GetChildren(List<Post> list, String parentUID, String parentname) {

        List<PostTree> tree = new ArrayList<PostTree>();

        for (Post post : list) {
            if (post.getParentuid().equalsIgnoreCase(parentUID)) {

                PostTree node = new PostTree(post);
                node.setParentname(parentname);
                node.setChildren(this.GetChildren(list, post.getUid(), node.getName()));
                tree.add(node);
            }
                        
        }

        return tree;
    }
    
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String UpdatePost(Post post) {
    	post.setEditby(webUsrUtil.CurrentUser().getEmployeeName());
		this.postService.UpdatePost(post);
		return JsonUtil.ResponseSuccessfulMessage("修改成功");
	}
  
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public String CreatePost(Post post) {
    	post.setUid(UUID.randomUUID().toString());
    	post.setCreateby(webUsrUtil.CurrentUser().getEmployeeName());
    	this.postService.CreatePost(post);
    	return JsonUtil.ResponseSuccessfulMessage("添加成功");
		
	}
    
//    @RequestMapping(value = "save", method = RequestMethod.POST)
//    @ResponseBody
//    public String SavePost(Post post){
//    	try {
//    		if(post.getUid().equalsIgnoreCase("0")){
//    			 post.setUid(UUID.randomUUID().toString());
//                 post.setCreateby(webUsrUtil.CurrentUser().getEmployeeName());
//                 this.postService.CreatePost(post);
//    		}else {
//                post.setEditby(webUsrUtil.CurrentUser().getEmployeeName());
//                this.postService.UpdatePost(post);
//            }
//    		
//			return JsonUtil.ResponseFailureMessage("保存成功");
//		} catch (Exception e) {
//			// TODO: handle exception
//			return JsonUtil.ResponseFailureMessage("保存失败");
//		}
//    }
    
    @RequestMapping(value = "tree/{postname}", method = RequestMethod.GET)
    @ResponseBody
    public String GetPostTreeSet(@PathVariable String postname) {
    	 List<Post> list = this.postService.GetPostSet(postname);

         List<PostTree> trees = this.InitOrgTree(list);

         return JsonUtil.ResponseSuccessfulMessage(trees);
	}
    
    @RequestMapping(value = "/getone/{uid}")
    @ResponseBody
    public String GetOnePost(@PathVariable String uid) {
        Post post = this.postService.GetOnePost(uid);
        Post parentPost = this.postService.GetOnePost(post.getParentuid());
        if (parentPost != null) {
            post.setParentname(parentPost.getName());
        }

        return JsonUtil.ResponseSuccessfulMessage(post);
    }
}
