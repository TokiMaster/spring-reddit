package swt.reddit.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoDTO implements Serializable {
    private final String description;
    private final String displayName;
}
