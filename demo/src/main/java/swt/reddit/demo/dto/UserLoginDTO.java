package swt.reddit.demo.dto;

import lombok.*;
import swt.reddit.demo.model.User;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginDTO {

    private String username;
    private String password;

    public UserLoginDTO(User user){
        this(user.getUsername(), user.getPassword());
    }

}
