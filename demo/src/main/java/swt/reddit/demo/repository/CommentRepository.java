package swt.reddit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt.reddit.demo.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}