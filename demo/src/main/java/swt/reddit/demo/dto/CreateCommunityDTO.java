package swt.reddit.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CreateCommunityDTO implements Serializable {

    @NotNull
    private final String name;
    @NotNull
    private final String description;
    private final MultipartFile file;

}
