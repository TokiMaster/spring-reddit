package swt.reddit.demo.service.serviceImpl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    public PostServiceImpl(PostRepository postRepository, IndexPostRepository indexPostRepository) {
        this.postRepository = postRepository;
        this.indexPostRepository = indexPostRepository;
    }

    public Optional<String> parsePdf(MultipartFile file) {
        try (var pdfInputStream = file.getInputStream(); var pddDocument = PDDocument.load(pdfInputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return Optional.of(pdfStripper.getText(pddDocument));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post createPost(Post post, MultipartFile pdfFile){
        Post savePost = postRepository.save(post);
        Optional<String> pdfText = parsePdf(pdfFile);
        IndexPost indexPost = new IndexPost(savePost, pdfText.get());
        indexPostRepository.save(indexPost);
        return savePost;
    }

    @Override
    public Optional<Post> findPostById(Long id){
        return postRepository.findById(id);
    }

    @Override
    public Post updatePost(Post post){
       return postRepository.save(post);
    }

    @Override
    public void deletePost(Post post){
        postRepository.save(post);
    }

    @Override
    public List<Post> findPostsByCommunityId(Long id) {
        return postRepository.findPostsByCommunityId(id);
    }
}
