package swt.reddit.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Administrator extends User {

    @Override
    public GrantedAuthority getRole() {
        return new SimpleGrantedAuthority("ADMIN");
    }
}
