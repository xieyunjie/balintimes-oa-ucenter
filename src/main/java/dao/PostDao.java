package dao;

import java.util.List;

import model.Post;
import tuples.TuplePage;

public interface PostDao {
	List<Post> GetPostList();

	TuplePage<List<Post>, Integer> GetPostListByPage(Post post, int page,
			int pageSize);

	void UpdatePost(Post post);

	void DetetePost(String uid);

	boolean CreatePost(Post post);

	List<Post> GetPostSet(String PostName);

	Post GetOnePost(String uid);
}
