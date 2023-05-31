package swt.reddit.demo.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import swt.reddit.demo.model.IndexCommunity;

public interface IndexCommunityRepository extends ElasticsearchRepository<IndexCommunity, Long> {
}
