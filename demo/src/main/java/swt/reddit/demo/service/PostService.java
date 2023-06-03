package swt.reddit.demo.service;

import org.springframework.web.multipart.MultipartFile;
import swt.reddit.demo.model.IndexPost;
import swt.reddit.demo.model.Post;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> findAll();
    @Transactional
    Post createPost(Post post, MultipartFile pdfFile);
    Optional<Post> findPostById(Long id);
    Post updatePost(Post post);
    void deletePost(Post post);
    List<Post> findPostsByCommunityId(Long id);
    Iterable<IndexPost> searchPosts(String pdfContent, String title, String text);

}
