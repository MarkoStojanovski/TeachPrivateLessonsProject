package spring.learnteachsubject.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.learnteachsubject.model.DTO.LoginDTO;
import spring.learnteachsubject.model.DTO.RegisterDTO;
import spring.learnteachsubject.model.User;
import spring.learnteachsubject.model.enumeration.Role;
import spring.learnteachsubject.service.AuthService;
import spring.learnteachsubject.service.UserService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final AuthService authService;

    public UserRestController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @RequestBody RegisterDTO registerDTO
    ) {
        User user = this.userService.register(
                registerDTO.getEmail(),
                registerDTO.getPassword(),
                registerDTO.getRepeatPassword(),
                registerDTO.getRole()
        );
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(
            @RequestBody LoginDTO loginDTO
    ) {
        User user = this.authService.login(loginDTO.getEmail(), loginDTO.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/api/roles")
    public List<String> getRoles() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .toList();
    }
}
