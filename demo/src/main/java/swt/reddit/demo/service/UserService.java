package swt.reddit.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swt.reddit.demo.dto.UserDTO;
import swt.reddit.demo.model.User;
import swt.reddit.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User register(UserDTO userDTO){
        return  userRepository.save(new User(userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail(),
                LocalDateTime.now(), userDTO.getDisplayName()));
    }

}
