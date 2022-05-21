package swt.reddit.demo.dto;

import lombok.*;
import java.io.Serializable;


@Data
public class UserLoginDTO implements Serializable {

    private final String username;
    private final String password;

}
