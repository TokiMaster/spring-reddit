package swt.reddit.demo.service;

import org.springframework.web.multipart.MultipartFile;
import swt.reddit.demo.model.Community;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CommunityService {

    Optional<Community> findCommunityById(Long id);
    List<Community> findAll();
    @Transactional
    Community createCommunity(Community community, MultipartFile file);
    Community updateCommunity(Community community);
    void deleteCommunity(Community community);
    List<Community> findByPdfContent(String pdfContent);

}
