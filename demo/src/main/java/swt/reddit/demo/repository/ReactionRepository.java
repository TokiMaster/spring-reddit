package swt.reddit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt.reddit.demo.model.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}