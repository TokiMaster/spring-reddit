package swt.reddit.demo.service;

import swt.reddit.demo.model.Community;
import swt.reddit.demo.model.Post;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CommunityService {
    Optional<Community> findCommunityById(Long id);
    List<Community> findAll();
    @Transactional
    Community createCommunity(Community community);
    Community updateCommunity(Community community);
    void deleteCommunity(Community community);
}
