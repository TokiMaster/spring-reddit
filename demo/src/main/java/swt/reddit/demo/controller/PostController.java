package swt.reddit.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import swt.reddit.demo.dto.PostDTO;
import swt.reddit.demo.dto.PostUpdateDTO;
import swt.reddit.demo.model.Community;
import swt.reddit.demo.model.Post;
import swt.reddit.demo.model.User;
import swt.reddit.demo.service.CommunityService;
import swt.reddit.demo.service.serviceImpl.PostServiceImpl;
import swt.reddit.demo.service.serviceImpl.UserServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CommunityService communityService;

    @GetMapping()
    public ResponseEntity<List<PostDTO>> getAllPosts(){

        List<Post> posts = postService.findAll();

        List<PostDTO> postsDTO = new ArrayList<>();
        for(Post post : posts){
            postsDTO.add(new PostDTO(post.getCommunity().getId(), post.getTitle(), post.getText(), post.getCreationDate(), post.getImagePath(), post.getUser().getUsername()));
        }

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOnePost(@PathVariable("id") Long id){
        Optional<Post> post = postService.findPostById(id);
        if(post.isEmpty()){
            return ResponseEntity.badRequest().body("Post with given id doesn't exist");
        }
        PostDTO postDTO = new PostDTO(post.get().getCommunity().getId(), post.get().getTitle(), post.get().getText(), post.get().getCreationDate(), post.get().getImagePath(), post.get().getUser().getUsername());
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping()
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    @Transactional
    public ResponseEntity<?> createPost(@RequestBody @Valid PostDTO postDTO, BindingResult result, Authentication auth){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid json");
        }
        User user = userService.findByUsername(auth.getName());
        Optional<Community> community = communityService.findCommunityById(postDTO.getCommunityId());
        if(community.isEmpty()){
            return ResponseEntity.badRequest().body("Community with given id doesn't exist!");
        }
        Post post = new Post(postDTO.getTitle(), postDTO.getText(), LocalDateTime.now(), postDTO.getImagePath(), user, community.get());
        var createdPost = postService.createPost(post);
        return ResponseEntity.ok(createdPost.getId());
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<?> editPost(@RequestBody @Valid PostUpdateDTO postDTO, BindingResult result, @PathVariable("id") Long id){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid json");
        }
        Optional<Post> post = postService.findPostById(id);
        if (post.isPresent()){
            if(postDTO.getText() != null){
                post.get().setText(postDTO.getText());
            }
            if(postDTO.getImagePath() != null){
                post.get().setImagePath(postDTO.getImagePath());
            }
            if(postDTO.getTitle() != null){
                post.get().setTitle(postDTO.getTitle());
            }
        }else{
            return ResponseEntity.badRequest().body("Post with given id doesn't exist");
        }
        var updatedPost = postService.updatePost(post.get());
        return ResponseEntity.ok(updatedPost.getId());
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id){
        Optional<Post> post = postService.findPostById(id);
        if(post.isEmpty()){
            return ResponseEntity.badRequest().body("Post with given id doesn't exist");
        }
        postService.deletePost(post.get());
        return ResponseEntity.ok("Deleted post with id: " + id);
    }
}
