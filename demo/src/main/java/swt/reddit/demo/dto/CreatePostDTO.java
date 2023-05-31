package swt.reddit.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CreatePostDTO implements Serializable {

    @NotNull
    private final Long communityId;
    @NotNull
    private final String title;
    @NotNull
    private final String text;
    private final String username;
    private final MultipartFile file;

}
