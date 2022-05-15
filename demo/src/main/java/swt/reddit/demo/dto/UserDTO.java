package swt.reddit.demo.dto;

import lombok.*;
import swt.reddit.demo.model.User;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String username;
    private String password;
    private String email;
    private String avatar;
    private String description;
    private String displayName;

    public UserDTO(User user){
        this(user.getUsername(), user.getPassword(), user.getEmail(), user.getAvatar(), user.getDescription(), user.getDisplayName());
    }

}
