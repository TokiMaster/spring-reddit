package swt.reddit.demo.service.serviceImpl;

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

    public CommunityServiceImpl(CommunityRepository communityRepository, IndexCommunityRepository indexCommunityRepository) {
        this.communityRepository = communityRepository;
        this.indexCommunityRepository = indexCommunityRepository;
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
}
