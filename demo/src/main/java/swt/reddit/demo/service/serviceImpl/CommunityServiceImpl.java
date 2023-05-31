package swt.reddit.demo.service.serviceImpl;

import org.springframework.stereotype.Service;
import swt.reddit.demo.model.Community;
import swt.reddit.demo.repository.CommunityRepository;
import swt.reddit.demo.service.CommunityService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
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
    public Community createCommunity(Community community) {
        return communityRepository.save(community);
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
