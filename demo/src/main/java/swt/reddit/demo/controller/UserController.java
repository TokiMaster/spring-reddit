package swt.reddit.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import swt.reddit.demo.dto.UserDTO;
import swt.reddit.demo.dto.UserLoginDTO;
import swt.reddit.demo.dto.UserPasswordDTO;
import swt.reddit.demo.model.User;
import swt.reddit.demo.security.TokenUtils;
import swt.reddit.demo.service.serviceImpl.UserServiceImpl;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService, TokenUtils tokenUtils){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping()
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        List<User> users = userService.findAll();

        List<UserDTO> userDTO = new ArrayList<>();
        for(User user : users){
            userDTO.add(new UserDTO(user.getUsername(), user.getPassword(), user.getEmail(), user.getDisplayName()));
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getOneUser(@PathVariable("id") Long id){
        Optional<User> user = userService.findUserById(id);
        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("User with given id doesn't exist");
        }
        UserDTO userDTO = new UserDTO(user.get().getUsername(), user.get().getPassword(), user.get().getEmail(), user.get().getDisplayName());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
            return ResponseEntity.ok(tokenUtils.generateToken(userDetails));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO userDTO, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid json");
        }
        User user = userService.findByUsername(userDTO.getUsername());
        if(user != null){
            return ResponseEntity.badRequest().body("Username already taken.");
        }
        String password = passwordEncoder.encode(userDTO.getPassword());
        User user1 = new User(userDTO.getUsername(), password, userDTO.getEmail(), LocalDateTime.now(), userDTO.getDisplayName());
        return ResponseEntity.ok(userService.register(user1));
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<?> changePassword(@RequestBody @Valid UserPasswordDTO passwordDTO, BindingResult result,
                        @PathVariable("id") Long id, Authentication auth){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body("Password must contain minimum eight characters, " +
                    "at least one uppercase letter, one lowercase letter, one number and one special character");
        }
        User loggedUser = userService.findByUsername(auth.getName());
        Optional<User> user = userService.findUserById(id);
        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("User with given id doesn't exist");
        } else{
            if(!loggedUser.getId().equals(user.get().getId())){
                return ResponseEntity.badRequest().body("Forbidden!");
            }
            if(passwordEncoder.matches(passwordDTO.getPassword(), user.get().getPassword())){
                String newPassword = passwordEncoder.encode(passwordDTO.getNewPassword());
                user.get().setPassword(newPassword);
                userService.updateUser(user.get());
                return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
            }else {
                return ResponseEntity.badRequest().body("Incorrect password");
            }
        }
    }

}
