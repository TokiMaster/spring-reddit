package swt.reddit.demo.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    @Pattern(regexp = "[A-Za-z0-9_]{3,21}$")
    private final String username;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    private final String password;

    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private final String email;

    @Length(min = 2)
    private final String displayName;

}
