package swt.reddit.demo.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class PostUpdateDTO implements Serializable {
    private final String title;
    private final String text;
    private final String imagePath;
}
