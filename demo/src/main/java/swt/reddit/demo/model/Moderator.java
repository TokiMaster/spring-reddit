package swt.reddit.demo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("moderator")
public class Moderator extends User {

}
