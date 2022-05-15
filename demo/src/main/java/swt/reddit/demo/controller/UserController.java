package swt.reddit.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swt.reddit.demo.dto.UserDTO;
import swt.reddit.demo.model.User;
import swt.reddit.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        List<User> users = userService.findAll();

        List<UserDTO> userDTO = new ArrayList<>();
        for(User user : users){
            userDTO.add(new UserDTO(user));
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


}
