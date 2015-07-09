package dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mappers.PostMapper;
import model.Post;

import org.springframework.stereotype.Repository;

import tuples.TuplePage;
import dao.PostDao;

@Repository("PostDao")
public class PostDaoImpl implements PostDao {

	@Resource
	private PostMapper postMapper;

	public List<Post> GetPostList() {
		// TODO Auto-generated method stub
		return this.postMapper.GetPostList();
	}

	public TuplePage<List<Post>, Integer> GetPostListByPage(Post post,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>(4);
		params.put("parentuid", post.getParentuid());
		params.put("uid", post.getUid());
		params.put("name", post.getName());
		params.put("organizationuid", post.getOrganizationuid());
		List<Post> list = postMapper.GetPostListByPage(params);
		int total = list.size();
		return new TuplePage<List<Post>, Integer>(list, total);
	}

	public void UpdatePost(Post post) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>(9);		
		params.put("uid", post.getUid());
		params.put("name", post.getName());
		params.put("parentuid", post.getParentuid());
		params.put("organizationuid", post.getOrganizationuid());
		params.put("comment", post.getComment());
		params.put("createby", post.getCreateby());
		params.put("createtime", post.getCreatetime());
		params.put("editby", post.getEditby());
		params.put("edittime", post.getEdittime());
		
		this.postMapper.UpdatePost(params);
	}
	
	public void DetetePost(String uid) {
		// TODO Auto-generated method stub
		this.postMapper.DeletePost(uid);
	}
	
	public boolean CreatePost(Post post) {
		// TODO Auto-generated method stub
		try {
			this.postMapper.CreatePost(post);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}		
	}

	
	public List<Post> GetPostSet(String PostName) {
		// TODO Auto-generated method stub		
		return this.postMapper.GetPostSet(PostName);
	}

	@Override
	public Post GetOnePost(String uid) {
		// TODO Auto-generated method stub
		return this.postMapper.GetOnePost(uid);
	}

}
