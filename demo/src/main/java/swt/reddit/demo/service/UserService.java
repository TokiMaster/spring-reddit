package swt.reddit.demo.service;

import swt.reddit.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    Optional<User> findUserById(Long id);
    User register(User user);
    User updateUser(User user);
}
