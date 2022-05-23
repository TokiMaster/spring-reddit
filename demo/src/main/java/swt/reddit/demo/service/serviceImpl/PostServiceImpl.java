package swt.reddit.demo.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swt.reddit.demo.model.Post;
import swt.reddit.demo.repository.PostRepository;
import swt.reddit.demo.service.PostService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> findPostById(Long id){
        return postRepository.findById(id);
    }

    @Override
    public Post updatePost(Post post){
       return postRepository.save(post);
    }

    @Override
    public void deletePost(Post post){
        postRepository.delete(post);
    }
}
