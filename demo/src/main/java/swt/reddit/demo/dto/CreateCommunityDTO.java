package swt.reddit.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CreateCommunityDTO implements Serializable {
    @NotNull
    private final String name;
    @Length(min = 5)
    private final String description;
}
