package swt.reddit.demo.service.serviceImpl;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swt.reddit.demo.model.Community;
import swt.reddit.demo.model.IndexCommunity;
import swt.reddit.demo.repository.CommunityRepository;
import swt.reddit.demo.repository.IndexCommunityRepository;
import swt.reddit.demo.service.CommunityService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    private final IndexCommunityRepository indexCommunityRepository;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;


    public CommunityServiceImpl(CommunityRepository communityRepository, IndexCommunityRepository indexCommunityRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.communityRepository = communityRepository;
        this.indexCommunityRepository = indexCommunityRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @Override
    public Optional<Community> findCommunityById(Long id) {
        return communityRepository.findById(id);
    }

    @Override
    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    @Override
    @Transactional
    public Community createCommunity(Community community, MultipartFile file) {
        Community saveCommunity = communityRepository.save(community);
        IndexCommunity indexCommunity;
        if (file == null) {
            indexCommunity = new IndexCommunity(community);
        } else {
            Optional<String> pdfContent = PostServiceImpl.parsePdf(file);
            indexCommunity = new IndexCommunity(community, pdfContent.get());
        }
        indexCommunityRepository.save(indexCommunity);
        return saveCommunity;
    }

    @Override
    public Community updateCommunity(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public void deleteCommunity(Community community) {
        communityRepository.delete(community);
    }

    @Override
    public List<Community> findByPdfContent(String pdfContent) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("pdfContent", pdfContent));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<IndexCommunity> indexCommunities = elasticsearchRestTemplate.search(searchQuery, IndexCommunity.class, IndexCoordinates.of("reddit_community"));
        var ids = indexCommunities.map(c -> c.getContent().getId());
        return communityRepository.findAllById(ids);
    }

}
