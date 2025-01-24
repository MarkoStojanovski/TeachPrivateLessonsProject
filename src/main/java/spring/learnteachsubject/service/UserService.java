package spring.learnteachsubject.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import spring.learnteachsubject.model.User;
import spring.learnteachsubject.model.enumeration.Role;

import java.util.List;

public interface UserService extends UserDetailsService {

    User register(String email, String password, String repeatPassword, Role role);

    User updateUser(String email, Role role);

    List<User> findAll();

    void deleteUser(String email);


}
