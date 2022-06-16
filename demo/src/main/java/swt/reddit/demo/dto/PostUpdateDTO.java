package swt.reddit.demo.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class PostUpdateDTO implements Serializable {
    private final Long id;
    private final String text;
}
