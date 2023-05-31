package swt.reddit.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "community_id")
    private Community community;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Set<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Set<Reaction> reactions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Set<Report> reports;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "post_flair", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "flair_id"))
    private Set<Flair> flairs;

    @Column(columnDefinition="bit default 0")
    private boolean isDeleted;

    @Column
    private Integer karma;

    public Post(String title, String text, LocalDateTime creationDate, User user, Community community, Integer karma) {
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.user = user;
        this.community = community;
        this.karma = karma;
    }

}
