package swt.reddit.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swt.reddit.demo.model.Post;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private String title;
    private String text;
    private LocalDateTime creationDate;
    private String imagePath;

    public PostDTO(Post post){
        this(post.getTitle(), post.getText(), post.getCreationDate(), post.getImagePath());
    }

}
