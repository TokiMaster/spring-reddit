package swt.reddit.demo.dto;

import lombok.Data;
import swt.reddit.demo.model.ReactionType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ReactionDTO implements Serializable {

    @NotNull
    private final ReactionType type;

    @NotNull
    private final Long postID;

}
