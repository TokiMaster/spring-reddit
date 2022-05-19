package swt.reddit.demo.dto;

import lombok.*;
import swt.reddit.demo.model.Post;
import swt.reddit.demo.model.User;

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
    private String displayName;

    public PostDTO(Post post){
        this(post.getTitle(), post.getText(), post.getCreationDate(), post.getImagePath(), post.getUser().getDisplayName());
    }

}
