package swt.reddit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt.reddit.demo.model.Flair;

public interface FlairRepository extends JpaRepository<Flair, Long> {
}