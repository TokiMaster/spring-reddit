package swt.reddit.demo.service.serviceImpl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swt.reddit.demo.model.IndexCommunity;
import swt.reddit.demo.model.IndexPost;
import swt.reddit.demo.model.Post;
import swt.reddit.demo.repository.IndexPostRepository;
import swt.reddit.demo.repository.PostRepository;
import swt.reddit.demo.service.PostService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final IndexPostRepository indexPostRepository;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public PostServiceImpl(PostRepository postRepository, IndexPostRepository indexPostRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.postRepository = postRepository;
        this.indexPostRepository = indexPostRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    public static Optional<String> parsePdf(MultipartFile file) {
        try (var pdfInputStream = file.getInputStream(); var pddDocument = PDDocument.load(pdfInputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return Optional.of(pdfStripper.getText(pddDocument));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post createPost(Post post, MultipartFile pdfFile) {
        Post savePost = postRepository.save(post);
        IndexPost indexPost;
        if (pdfFile == null) {
            indexPost = new IndexPost(savePost);
        } else {
            Optional<String> pdfText = parsePdf(pdfFile);
            indexPost = new IndexPost(savePost, pdfText.get(), pdfFile.getOriginalFilename());
        }
        indexPostRepository.save(indexPost);
        return savePost;
    }

    @Override
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> findPostsByCommunityId(Long id) {
        return postRepository.findPostsByCommunityId(id);
    }

    @Override
    public Iterable<IndexPost> searchPosts(String pdfContent, String title, String text) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (pdfContent != null) {
            queryBuilder.must(QueryBuilders.matchQuery("pdfContent", pdfContent));
        }
        if (title != null) {
            queryBuilder.must(QueryBuilders.matchQuery("title", title));
        }
        if (text != null) {
            queryBuilder.must(QueryBuilders.matchQuery("text", text));
        }
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<IndexPost> indexCommunities = elasticsearchRestTemplate.search(searchQuery, IndexPost.class, IndexCoordinates.of("reddit_posts"));
        var ids = indexCommunities.map(p -> p.getContent().getId());
        return indexPostRepository.findAllById(ids);
    }

}
