package swt.reddit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt.reddit.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}