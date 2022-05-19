package swt.reddit.demo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private ReportReason reason;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    @Column
    private boolean accepted;

    @ManyToOne()
    private User user;

    @ManyToOne()
    private Report report;
}
