package swt.reddit.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import swt.reddit.demo.model.Rule;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CommunityDTO implements Serializable {

    @NotNull
    private final String name;
    @Length(min = 5)
    private final String description;
    private final LocalDateTime creationDate;
}
