package mappers;

import java.util.List;
import java.util.Map;

import model.Post;


public interface PostMapper
{
	List<Post> GetPostList();
	
	List<Post> GetPostListByPage(Map<String, Object> params);
	
	List<Post> GetPostSet(String PostName);
	
	Post GetOnePost(String uid);
	
	void UpdatePost(Map<String, Object> params);
	
	void DeletePost(String uid);
	
	void CreatePost(Post post);
	
	void DeleteUserPost(String uid);
	
	List<Post> GetPostByEmployee(String useruid);
	
	List<Post> GetPostParent(Map<String, Object> params);
}