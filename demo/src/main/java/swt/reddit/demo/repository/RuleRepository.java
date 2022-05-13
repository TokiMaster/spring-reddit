package swt.reddit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt.reddit.demo.model.Rule;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}