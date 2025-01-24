package spring.learnteachsubject.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.learnteachsubject.model.Professor;
import spring.learnteachsubject.model.Student;
import spring.learnteachsubject.model.User;
import spring.learnteachsubject.model.enumeration.Role;
import spring.learnteachsubject.model.exceptions.PasswordsDoNotMatchException;
import spring.learnteachsubject.model.exceptions.UserNotFoundException;
import spring.learnteachsubject.model.exceptions.UsernameOrPasswordEmptyException;
import spring.learnteachsubject.repository.ProfessorRepository;
import spring.learnteachsubject.repository.StudentRepository;
import spring.learnteachsubject.repository.UserRepository;
import spring.learnteachsubject.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, StudentRepository studentRepository, ProfessorRepository professorRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User register(String email, String password, String repeatPassword, Role role) {
        if(email == null || email.isEmpty() || password == null || password.isEmpty()){
            throw new UsernameOrPasswordEmptyException();
        }
        if(!password.equals(repeatPassword)){
            throw new PasswordsDoNotMatchException();
        }
        User user = new User(
                email,
                passwordEncoder.encode(password),
                Role.STUDENT
        );
        user =  this.userRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    public User updateUser(String email, Role role) {
        User user = this.userRepository.findByEmail(email);
        if (user == null){
            throw new UserNotFoundException();
        }
        user.setRole(role);
        return this.userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteUser(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        studentRepository.updateUserToNullByEmail(email);
        professorRepository.updateUserToNullByEmail(email);
        this.userRepository.deleteById(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Username Not Found.");
        }
        return user;
    }
}
