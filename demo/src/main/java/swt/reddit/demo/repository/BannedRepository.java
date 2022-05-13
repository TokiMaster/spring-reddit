package swt.reddit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt.reddit.demo.model.Banned;

public interface BannedRepository extends JpaRepository<Banned, Long> {
}