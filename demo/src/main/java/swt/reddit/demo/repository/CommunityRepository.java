package swt.reddit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt.reddit.demo.model.Community;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}