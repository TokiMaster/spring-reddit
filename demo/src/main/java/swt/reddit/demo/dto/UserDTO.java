package swt.reddit.demo.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import swt.reddit.demo.model.User;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    @Pattern(regexp = "[A-Za-z0-9_]{3,21}$")
    private String username;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    private String password;

    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    @Length(min = 2)
    private String displayName;

    public UserDTO(User user){
        this(user.getUsername(), user.getPassword(), user.getEmail(), user.getDisplayName());
    }

}
