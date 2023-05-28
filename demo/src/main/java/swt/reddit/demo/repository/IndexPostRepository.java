package swt.reddit.demo.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import swt.reddit.demo.model.IndexPost;

public interface IndexPostRepository extends ElasticsearchRepository<IndexPost, Long> {
}
