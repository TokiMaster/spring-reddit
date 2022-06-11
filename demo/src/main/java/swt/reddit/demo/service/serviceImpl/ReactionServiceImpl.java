package swt.reddit.demo.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swt.reddit.demo.dto.ReactionDTO;
import swt.reddit.demo.model.Reaction;
import swt.reddit.demo.repository.ReactionRepository;
import swt.reddit.demo.service.ReactionService;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public Reaction createReaction(Reaction reaction) {
        return reactionRepository.save(reaction);
    }

    @Override
    public List<Reaction> findReactionsByPostId(Long id) {
        return reactionRepository.findReactionsByPostId(id);
    }

    @Override
    public List<Reaction> findAll() {
        return reactionRepository.findAll();
    }

    @Override
    public List<Reaction> findReactionsByUserId(Long id) {
        return reactionRepository.findReactionsByUserId(id);
    }

    @Override
    public Optional<Reaction> findOne(Long id) {
        return reactionRepository.findById(id);
    }

    @Override
    public Reaction updateReaction(Reaction reaction) {
        return reactionRepository.save(reaction);
    }
}
