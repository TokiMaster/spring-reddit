package swt.reddit.demo.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CreatePostDTO implements Serializable {

    @NotNull
    private final Long communityId;
    @NotNull
    private final String title;
    @Length(min = 5)
    private final String text;
    private final String username;

}
