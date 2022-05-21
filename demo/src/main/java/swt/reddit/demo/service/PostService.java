package swt.reddit.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swt.reddit.demo.dto.PostDTO;
import swt.reddit.demo.dto.PostUpdateDTO;
import swt.reddit.demo.model.Post;
import swt.reddit.demo.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    @Transactional
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public Optional<Post> findPostById(Long id){
        return postRepository.findById(id);
    }

    public Post updatePost(Post post){
       return postRepository.save(post);
    }

    public void deletePost(Post post){
        postRepository.delete(post);
    }
}
