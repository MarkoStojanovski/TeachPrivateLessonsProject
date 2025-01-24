package spring.learnteachsubject.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.User;
import spring.learnteachsubject.model.exceptions.InvalidUserCredentialsException;
import spring.learnteachsubject.model.exceptions.UsernameOrPasswordEmptyException;
import spring.learnteachsubject.repository.UserRepository;
import spring.learnteachsubject.service.AuthService;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new UsernameOrPasswordEmptyException();
        }
        User user = this.userRepository.findByEmail(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidUserCredentialsException();
        }
        return user;
    }




    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
