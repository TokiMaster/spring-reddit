package swt.reddit.demo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommunityDTO implements Serializable {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime creationDate;
}
