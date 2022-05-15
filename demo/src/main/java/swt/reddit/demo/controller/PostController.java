package swt.reddit.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swt.reddit.demo.dto.PostDTO;
import swt.reddit.demo.model.Post;
import swt.reddit.demo.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts(){

        List<Post> posts = postService.findAll();

        List<PostDTO> postsDTO = new ArrayList<>();
        for(Post post : posts){
            postsDTO.add(new PostDTO(post));
        }

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

}
