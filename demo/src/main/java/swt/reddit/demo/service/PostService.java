package swt.reddit.demo.service;

import swt.reddit.demo.model.Post;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAll();
    @Transactional
    Post createPost(Post post);
    Optional<Post> findPostById(Long id);
    Post updatePost(Post post);
    void deletePost(Post post);
}
