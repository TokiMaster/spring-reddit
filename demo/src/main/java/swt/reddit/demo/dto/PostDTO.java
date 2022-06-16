package swt.reddit.demo.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PostDTO implements Serializable {

    private final Long id;
    private final Long communityId;
    private final String title;
    private final String text;
    private final LocalDateTime creationDate;
    private final String username;
    private final Integer karma;

}
