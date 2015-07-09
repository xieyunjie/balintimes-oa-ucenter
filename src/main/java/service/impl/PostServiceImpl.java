package service.impl;

import java.util.List;

import javax.annotation.Resource;

import model.Post;

import org.springframework.stereotype.Service;

import service.PostService;
import tuples.TuplePage;
import annotation.CustomerTransactional;
import dao.PostDao;

@Service("postService")
public class PostServiceImpl implements PostService {
	
	@Resource
	private PostDao postDao;
	
	public List<Post> GetPostList() {
		return this.postDao.GetPostList();
	}

	
	public TuplePage<List<Post>, Integer> GetPostListByPage(Post post,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		return this.postDao.GetPostListByPage(post, page, pageSize);
	}

	 @CustomerTransactional
	public void UpdatePost(Post post) {
		// TODO Auto-generated method stub
		 this.postDao.UpdatePost(post);
	}

	 @CustomerTransactional
	public void DetetePost(String uid) {
		// TODO Auto-generated method stub
		 this.postDao.DetetePost(uid);
	}
	
	public boolean CreatePost(Post post) {
		// TODO Auto-generated method stub
		return this.postDao.CreatePost(post);
	}


	public List<Post> GetPostSet(String PostName) {
		// TODO Auto-generated method stub
		return this.postDao.GetPostSet(PostName);
	}


	public Post GetOnePost(String uid) {
		// TODO Auto-generated method stub
		return this.postDao.GetOnePost(uid);
	}
	
	

}
