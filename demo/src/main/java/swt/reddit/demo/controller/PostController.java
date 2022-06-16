package swt.reddit.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import swt.reddit.demo.dto.CreatePostDTO;
import swt.reddit.demo.dto.PostDTO;
import swt.reddit.demo.dto.PostUpdateDTO;
import swt.reddit.demo.dto.ReactionDTO;
import swt.reddit.demo.model.*;
import swt.reddit.demo.service.CommunityService;
import swt.reddit.demo.service.ReactionService;
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

    @Autowired
    private ReactionService reactionService;

    @GetMapping()
    public ResponseEntity<List<PostDTO>> getAllPosts(){

        List<Post> posts = postService.findAll();
        List<Reaction> reactions = reactionService.findAll();
        List<Reaction> upvote = new ArrayList<>();
        List<Reaction> downvote = new ArrayList<>();
        Integer karma = 0;

        List<PostDTO> postsDTO = new ArrayList<>();
        for(Post post : posts){
            if(!post.isDeleted()){
                for(Reaction reaction: reactions){
                    if(reaction.getPost().getId().equals(post.getId())){
                        if(reaction.getType().equals(ReactionType.UPVOTE)){
                            upvote.add(reaction);
                        }else{
                            downvote.add(reaction);
                        }
                    }
                }
                karma = upvote.size() - downvote.size();
                postsDTO.add(new PostDTO(post.getId(), post.getCommunity().getId(), post.getTitle(), post.getText(), post.getCreationDate(), post.getUser().getUsername(), karma));
            }
        }

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOnePost(@PathVariable("id") Long id){
        Optional<Post> post = postService.findPostById(id);
        if(post.isEmpty()){
            return ResponseEntity.badRequest().body("Post with given id doesn't exist");
        }
        PostDTO postDTO = new PostDTO(post.get().getId(), post.get().getCommunity().getId(), post.get().getTitle(), post.get().getText(), post.get().getCreationDate(), post.get().getUser().getUsername(), post.get().getKarma());
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping()
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    @Transactional
    public ResponseEntity<?> createPost(@RequestBody @Valid CreatePostDTO postDTO, BindingResult result, Authentication auth){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid json");
        }
        User user = userService.findByUsername(auth.getName());
        Optional<Community> community = communityService.findCommunityById(postDTO.getCommunityId());
        if(community.isEmpty()){
            return ResponseEntity.badRequest().body("Community with given id doesn't exist!");
        }
        Post post = new Post(postDTO.getTitle(), postDTO.getText(), LocalDateTime.now(), user, community.get(), 0);
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
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id, Authentication auth){
        Optional<Post> post = postService.findPostById(id);
        if(post.isEmpty()){
            return ResponseEntity.badRequest().body("Post with given id doesn't exist");
        }
        User loggedUser = userService.findByUsername(auth.getName());
        if(post.get().getUser().getUsername().equals(loggedUser.getUsername())){
            post.get().setDeleted(true);
            postService.deletePost(post.get());
            return ResponseEntity.ok("Deleted post with id: " + id);
        }else{
            return ResponseEntity.badRequest().body("Method not allowed!");
        }
    }

    @PostMapping("/{id}/vote")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<?> vote(@PathVariable("id") Long id, @RequestBody @Valid ReactionDTO reactionDTO,
                                    BindingResult result, Authentication auth){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid json");
        }
        Optional<Post> post = postService.findPostById(id);
        if(post.isEmpty()){
            return ResponseEntity.badRequest().body("Post with given id doesn't exist");
        }
        User loggedUser = userService.findByUsername(auth.getName());
        List<Reaction> reactions = reactionService.findReactionsByUserId(loggedUser.getId());
        if(!reactions.isEmpty()){
            for(Reaction reaction : reactions){
                if(reaction.getPost().getId().equals(post.get().getId()) && reaction.getType().equals(reactionDTO.getType())){
                    return ResponseEntity.badRequest().body("Can't react twice on the same post!");
                }else if(reaction.getPost().getId().equals(post.get().getId()) && !reaction.getType().equals(reactionDTO.getType())){
                    Optional<Reaction> reaction1 = reactionService.findOne(reaction.getId());
                    if(reaction1.isPresent()){
                        reaction1.get().setType(reactionDTO.getType());
                        var updatedReaction = reactionService.updateReaction(reaction1.get());
                        return ResponseEntity.ok(updatedReaction.getType());
                    }
                }
            }
        }
        Reaction reaction = new Reaction(reactionDTO.getType(), loggedUser, post.get());
        var createdReaction = reactionService.createReaction(reaction);
        return ResponseEntity.ok(createdReaction.getId());
    }
}
