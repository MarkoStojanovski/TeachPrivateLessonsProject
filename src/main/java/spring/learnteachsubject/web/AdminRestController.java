package spring.learnteachsubject.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.learnteachsubject.model.User;
import spring.learnteachsubject.model.enumeration.Role;
import spring.learnteachsubject.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = this.userService.findAll();
        return ResponseEntity.ok(users);
    }


    @PutMapping("/update-role")
    public ResponseEntity<String> updateRoleUser(
            @RequestParam String email,
            @RequestParam Role role
            ) {
        User user = this.userService.updateUser(email,role);
        return ResponseEntity.ok("Role is updated successfuly");
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        this.userService.deleteUser(email);
        return ResponseEntity.ok("Deleting User is successful");
    }


}
