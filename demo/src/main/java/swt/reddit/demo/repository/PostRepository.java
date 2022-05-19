package swt.reddit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt.reddit.demo.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}