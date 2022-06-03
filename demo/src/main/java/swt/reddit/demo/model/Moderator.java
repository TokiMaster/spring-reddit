package swt.reddit.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;

@Entity
@DiscriminatorValue("moderator")
public class Moderator extends User {

    @Override
    public GrantedAuthority getRole() {
        return new SimpleGrantedAuthority("ROLE_MODERATOR");
    }
}
