package swt.reddit.demo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private ReactionType type;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    @ManyToOne()
    private User user;

    @ManyToOne()
    private Post post;

    @ManyToOne()
    private Comment comment;
}
